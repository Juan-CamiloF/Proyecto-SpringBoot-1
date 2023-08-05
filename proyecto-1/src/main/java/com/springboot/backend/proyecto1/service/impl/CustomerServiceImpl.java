package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.model.Region;
import com.springboot.backend.proyecto1.controller.request.RequestCreateCustomer;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateCustomer;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.repository.ICustomerRepository;
import com.springboot.backend.proyecto1.exception.CustomerException;
import com.springboot.backend.proyecto1.service.ICustomerService;
import com.springboot.backend.proyecto1.service.IRegionService;
import com.springboot.backend.proyecto1.service.IUploadFileService;
import com.springboot.backend.proyecto1.exception.UploadFileException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Services for Customer
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

    Path fileStoragePath;
    String fileStorageLocation;

    private final ICustomerRepository customerRepository;
    private final IUploadFileService uploadFileService;
    private final IRegionService regionService;
    private final ModelMapper modelMapper;

    private static final String PATH = "/api/v1/customers/images/";

    public CustomerServiceImpl(ModelMapper modelMapper, ICustomerRepository customerRepository,
                               IUploadFileService uploadFileService, IRegionService regionService,
                               @Value("${filesStoragePath:temp}") String fileStorageLocation) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.uploadFileService = uploadFileService;
        this.regionService = regionService;
        this.fileStorageLocation = fileStorageLocation;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException e) {
            throw new UploadFileException("Error tratando de crear el directorio para la subida de archivos directorio");
        }
    }

    /**
     * Implementation method for save a Customer
     *
     * @param requestCreateCustomer - Customer data to create
     * @return {@link Customer}
     */
    @Override
    @Transactional
    public Customer save(RequestCreateCustomer requestCreateCustomer) {
        Customer customer = mapToEntity(requestCreateCustomer);
        if (customerRepository.existsByEmail(customer.getEmail()))
            throw new CustomerException("El Correo ya está registrado");
        return customerRepository.save(customer);
    }

    /**
     * Implementation method for get a Customer by id
     *
     * @param id to Customer
     * @return {@link Customer}
     */
    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) throw new CustomerException("El cliente no existe");
        return customer.get();
    }

    /**
     * Implementation method get a list of Customer
     *
     * @return List to {@link Customer}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll(Sort.by("id"));
    }

    /**
     * Implementation method get a page of Customer
     *
     * @return Page to {@link Customer}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAllPaginated(Integer pageNumber, Integer pageSize) {
        return customerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending()));
    }

    /**
     * Implementation method get a filtered list of Customer
     *
     * @param id - id Region
     * @return list to Customer
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAllByRegion(Long id, Integer pageNumber, Integer pageSize) {
        Region region = regionService.findById(id);
        return customerRepository.findAllByRegion(region, PageRequest.of(pageNumber, pageSize));
    }


    /**
     * Implementation method to update a Customer
     *
     * @param currentClient - current client
     * @param newClient     - new client
     * @return {@link Customer}
     */
    @Override
    @Transactional
    public Customer update(Customer currentClient, RequestUpdateCustomer newClient) {
        if (!Objects.equals(currentClient.getId(), newClient.getId()))
            throw new CustomerException("Cliente no válido para actualizar");

        if (!currentClient.getEmail().equals(newClient.getEmail())) {
            if (customerRepository.existsByEmail((newClient.getEmail())))
                throw new CustomerException("El correo ya está en uso");
            currentClient.setEmail(newClient.getEmail());
        }
        currentClient.setNames(newClient.getNames());
        currentClient.setSurnames(newClient.getSurnames());
        currentClient.setUpdateAt(LocalDateTime.now());
        return customerRepository.save(currentClient);
    }

    /**
     * Implementation method to delete a Customer
     *
     * @param id to Customer
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Customer> clienteOptional = customerRepository.findById(id);
        if (clienteOptional.isEmpty()) throw new CustomerException("El cliente no existe");
        Customer cliente = clienteOptional.get();
        if (cliente.getFilename() != null) uploadFileService.deleteFile(cliente.getFilename());
        customerRepository.deleteById(id);
    }

    /**
     * Implementation method if exists a Customer
     *
     * @param id to Customer
     * @return boolean
     */
    @Override
    @Transactional
    public boolean exists(Long id) {
        return customerRepository.existsById(id);
    }

    /**
     * Implementation method to upload resource a Customer
     *
     * @param customer -> customer
     * @param file     -> customer file
     * @return customer updated
     */
    @Override
    @Transactional
    public Customer uploadResource(Customer customer, MultipartFile file) {
        String fileName = uploadFileService.uploadFile(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH).path(fileName).toUriString();
        if (customer.getFilename() != null) {
            uploadFileService.deleteFile(customer.getFilename());
        }
        customer.setFilename(fileName);
        customer.setUpdateAt(LocalDateTime.now());
        customer.setUrlFilename(url);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Implementation method to get resource a Customer
     *
     * @param fileName -> Customer filename
     * @return resource
     */
    @Override
    public Resource getResource(String fileName) {
        Path path = Paths.get(fileStorageLocation).resolve(fileName).toAbsolutePath();
        return uploadFileService.getResource(path);
    }

    /**
     * Implementation method to delete resource a Customer
     *
     * @param customer -> customer
     * @param fileName -> customer filename
     * @return updated customer
     */
    @Override
    @Transactional
    public Customer deleteResource(Customer customer, String fileName) {
        Path path = Paths.get(fileStorageLocation).resolve(fileName).normalize();
        if (customer.getFilename() == null || uploadFileService.checkFile(path))
            throw new CustomerException("El archivo no existe");
        uploadFileService.deleteFile(fileName);
        customer.setFilename(null);
        customer.setUrlFilename(null);
        customer.setUpdateAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    /**
     * Method for create Customer by RequestCreateCustomer
     *
     * @param requestCreateCustomer -> requestCreateCustomer
     * @return Customer
     */
    private Customer mapToEntity(RequestCreateCustomer requestCreateCustomer) {
        return modelMapper.map(requestCreateCustomer, Customer.class);
    }

}
