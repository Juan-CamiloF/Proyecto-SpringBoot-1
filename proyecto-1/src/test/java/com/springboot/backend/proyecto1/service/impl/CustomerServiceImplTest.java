package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.controller.request.RequestCreateCustomer;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateCustomer;
import com.springboot.backend.proyecto1.data.CustomerData;
import com.springboot.backend.proyecto1.exception.CustomerException;
import com.springboot.backend.proyecto1.exception.UploadFileException;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.Region;
import com.springboot.backend.proyecto1.repository.ICustomerRepository;
import com.springboot.backend.proyecto1.repository.IRegionRepository;
import com.springboot.backend.proyecto1.service.IUploadFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    ICustomerRepository customerRepository;

    @Mock
    IRegionRepository regionRepository;

    @Mock
    IUploadFileService uploadFileService;

    CustomerServiceImpl customerService;

    RegionServiceImpl regionService;

    Region region;

    @Mock
    Resource mockResource;

    @BeforeEach
    void init() {
        region = CustomerData.REGION();
        regionService = new RegionServiceImpl(regionRepository);
        customerService = new CustomerServiceImpl(new ModelMapper(), customerRepository, uploadFileService, regionService, "upload");
    }

    @Test
    void testExceptionFileStorageLocation() {
        assertThrows(UploadFileException.class, this::createCustomerServiceWithInvalidFileStorageLocation,
                "Error tratando de crear el directorio para la subida de archivos directorio");
    }

    @Test
    void testSaveACustomer() {
        Customer customer = CustomerData.CUSTOMER_1();
        when(customerRepository.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        RequestCreateCustomer requestCreateCustomer = new RequestCreateCustomer();
        requestCreateCustomer.setNames(CustomerData.CUSTOMER_NAME_1);
        requestCreateCustomer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        requestCreateCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        requestCreateCustomer.setDateOfBirth(LocalDate.now());
        requestCreateCustomer.setRegion(region);
        Customer test = customerService.save(requestCreateCustomer);
        assertNotNull(test);
        assertEquals(customer, test);
        verify(customerRepository).existsByEmail(any(String.class));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testSaveACustomerWithEmailInUse() {
        when(customerRepository.existsByEmail(anyString())).thenReturn(Boolean.TRUE);
        RequestCreateCustomer requestCreateCustomer = new RequestCreateCustomer();
        requestCreateCustomer.setNames(CustomerData.CUSTOMER_NAME_1);
        requestCreateCustomer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        requestCreateCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        requestCreateCustomer.setDateOfBirth(LocalDate.now());
        requestCreateCustomer.setRegion(region);
        assertThrows(CustomerException.class, () -> customerService.save(requestCreateCustomer));
        verify(customerRepository).existsByEmail(any(String.class));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testFindCustomerById() {
        Customer customer = CustomerData.CUSTOMER_1();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        Customer test = customerService.findById(anyLong());
        assertNotNull(test);
        assertEquals(customer, test);
        verify(customerRepository).findById(anyLong());
    }

    @Test
    void testFindCustomerByNonExistentId() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        long any = anyLong();
        assertThrows(CustomerException.class, () -> customerService.findById(any));
        verify(customerRepository).findById(anyLong());
    }

    @Test
    void testListFindAllCustomer() {
        when(customerRepository.findAll(Sort.by("id"))).thenReturn(CustomerData.CUSTOMER_LIST());
        List<Customer> customerList = customerService.findAll();
        assertEquals(2, customerList.size());
        assertEquals(customerList.get(0).getNames(), CustomerData.CUSTOMER_1().getNames());
        assertEquals(customerList.get(1).getNames(), CustomerData.CUSTOMER_2().getNames());
        verify(customerRepository).findAll(Sort.by("id"));
    }

    @Test
    void testPaginatedFindAllCustomers() {
        Page<Customer> customerPage = CustomerData.CUSTOMER_PAGE();
        when(customerRepository.findAll(PageRequest.of(0, 10, Sort.by("id").ascending()))).thenReturn(customerPage);
        assertEquals(2, customerService.findAllPaginated(0, 10).getTotalElements());
    }

    @Test
    void testFindAllByRegion() {
        Region region = CustomerData.REGION();
        Page<Customer> customerPage = CustomerData.CUSTOMER_PAGE();
        when(customerRepository.findAllByRegion(any(Region.class), any(PageRequest.class))).thenReturn(customerPage);
        when(regionRepository.findById(anyLong())).thenReturn(Optional.of(region));
        long test = customerService.findAllByRegion(1L, 0, 10).getTotalElements();
        assertEquals(2, test);
    }

    @Test
    void testUpdateACustomer() {
        Customer customer = CustomerData.CUSTOMER_1();
        Customer customerUpdate = CustomerData.CUSTOMER_1_UPDATE_NAME();
        when(customerRepository.save(any(Customer.class))).thenReturn(customerUpdate);
        RequestUpdateCustomer requestUpdateCustomer = new RequestUpdateCustomer();
        requestUpdateCustomer.setId(CustomerData.CUSTOMER_ID_1);
        requestUpdateCustomer.setNames(CustomerData.CUSTOMER_NAME_2);
        requestUpdateCustomer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        requestUpdateCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        requestUpdateCustomer.setDateOfBirth(LocalDate.now());
        requestUpdateCustomer.setRegion(region);
        Customer test = customerService.update(customer, requestUpdateCustomer);
        assertNotNull(test);
        assertEquals(customerUpdate, test);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testUpdateACustomerWithADifferentId() {
        Customer customer = CustomerData.CUSTOMER_1();
        RequestUpdateCustomer requestUpdateCustomer = new RequestUpdateCustomer();
        requestUpdateCustomer.setId(CustomerData.CUSTOMER_ID_2);
        requestUpdateCustomer.setNames(CustomerData.CUSTOMER_NAME_2);
        requestUpdateCustomer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        requestUpdateCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        requestUpdateCustomer.setDateOfBirth(LocalDate.now());
        requestUpdateCustomer.setRegion(region);
        assertThrows(CustomerException.class, () -> customerService.update(customer, requestUpdateCustomer));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testUpdateACustomerWithADifferentEmail() {
        Customer customer = CustomerData.CUSTOMER_1();
        Customer customerUpdate = CustomerData.CUSTOMER_1_UPDATE_EMAIL();
        when(customerRepository.save(any(Customer.class))).thenReturn(customerUpdate);
        RequestUpdateCustomer requestUpdateCustomer = new RequestUpdateCustomer();
        requestUpdateCustomer.setId(CustomerData.CUSTOMER_ID_1);
        requestUpdateCustomer.setNames(CustomerData.CUSTOMER_NAME_1);
        requestUpdateCustomer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        requestUpdateCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_2);
        requestUpdateCustomer.setDateOfBirth(LocalDate.now());
        requestUpdateCustomer.setRegion(region);
        when(customerRepository.existsByEmail(any(String.class))).thenReturn(Boolean.FALSE);
        Customer test = customerService.update(customer, requestUpdateCustomer);
        assertNotNull(test);
        assertEquals(customerUpdate, test);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testUpdateACustomerWithADifferentEmailInUse() {
        Customer customer = CustomerData.CUSTOMER_1();
        RequestUpdateCustomer requestUpdateCustomer = new RequestUpdateCustomer();
        requestUpdateCustomer.setId(CustomerData.CUSTOMER_ID_1);
        requestUpdateCustomer.setNames(CustomerData.CUSTOMER_NAME_2);
        requestUpdateCustomer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        requestUpdateCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_2);
        requestUpdateCustomer.setDateOfBirth(LocalDate.now());
        requestUpdateCustomer.setRegion(region);
        when(customerRepository.existsByEmail(any(String.class))).thenReturn(Boolean.TRUE);
        assertThrows(CustomerException.class, () -> customerService.update(customer, requestUpdateCustomer));
        verify(customerRepository).existsByEmail(any());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testDeleteACustomer() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerData.CUSTOMER_1()));
        customerService.delete(CustomerData.CUSTOMER_ID_1);
        verify(customerRepository).findById(anyLong());
        verify(customerRepository).deleteById(anyLong());
    }

    @Test
    void testDeleteACustomerWithFile() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerData.CUSTOMER_1_WITH_FILE()));
        customerService.delete(CustomerData.CUSTOMER_ID_1);
        verify(customerRepository).findById(anyLong());
        verify(customerRepository).deleteById(anyLong());
    }

    @Test
    void testDeleteACustomerByNonExistentId() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomerException.class, () -> customerService.delete(CustomerData.CUSTOMER_ID_1));
        verify(customerRepository).findById(anyLong());
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    void testExistsCustomer() {
        when(customerRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
        assertTrue(customerService.exists(anyLong()));
        verify(customerRepository).existsById(anyLong());
    }

    @Test
    void testUploadResource() {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", imageBytes);
        when(uploadFileService.uploadFile(file)).thenReturn("image.png");
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Customer customer = CustomerData.CUSTOMER_1();
        Customer customerWithFile = CustomerData.CUSTOMER_1_WITH_FILE();
        Customer test = customerService.uploadResource(customer, file);
        assertNotNull(test);
        assertEquals(customerWithFile.getFilename(), test.getFilename());
        assertEquals(customerWithFile.getUrlFilename(), test.getUrlFilename());
        verify(uploadFileService).uploadFile(any(MultipartFile.class));
    }

    @Test
    void testUploadResourceDeletedThePreviousOne() {
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("file", "image2.png", "image/png", imageBytes);
        when(uploadFileService.uploadFile(file)).thenReturn("image2.png");
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Customer customer = CustomerData.CUSTOMER_1_WITH_FILE();
        Customer updatedCustomer = CustomerData.CUSTOMER_1_UPDATE_WITH_FILE();
        Customer test = customerService.uploadResource(customer, file);
        assertNotNull(test);
        assertEquals(updatedCustomer.getFilename(), test.getFilename());
        assertEquals(updatedCustomer.getUrlFilename(), test.getUrlFilename());
        verify(uploadFileService).deleteFile(anyString());
        verify(uploadFileService).uploadFile(any(MultipartFile.class));
    }

    @Test
    void testGetResource() {
        Customer customer = CustomerData.CUSTOMER_1_WITH_FILE();
        when(uploadFileService.getResource(any(Path.class))).thenReturn(mockResource);
        Resource resource = customerService.getResource(customer.getFilename());
        assertNotNull(resource);
        assertEquals(mockResource, resource);
        verify(uploadFileService).getResource(any(Path.class));
    }

    @Test
    void testDeleteResource() {
        Customer customer = CustomerData.CUSTOMER_1();
        when(uploadFileService.checkFile(any(Path.class))).thenReturn(Boolean.FALSE);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer customerWithFile = CustomerData.CUSTOMER_1_WITH_FILE();
        Customer test = customerService.deleteResource(customerWithFile, customerWithFile.getFilename());
        assertNotNull(test);
        assertNull(test.getFilename());
        assertNull(test.getUrlFilename());
        assertNotEquals(customerWithFile.getUpdateAt(), test.getUpdateAt());
        verify(uploadFileService).checkFile(any(Path.class));
        verify(uploadFileService).deleteFile(anyString());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testDeleteResourceWithNonExistentFile() {
        Customer customerWithFile = CustomerData.CUSTOMER_1_WITH_FILE();
        when(uploadFileService.checkFile(any(Path.class))).thenReturn(Boolean.TRUE);
        assertThrows(CustomerException.class, () -> customerService.deleteResource(customerWithFile, customerWithFile.getFilename()), "El archivo no existe");
        verify(uploadFileService).checkFile(any(Path.class));
        verify(uploadFileService, never()).deleteFile(anyString());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomerResourceWithoutFile() {
        Customer customer = CustomerData.CUSTOMER_1();
        Customer customerWithFile = CustomerData.CUSTOMER_1_WITH_FILE();
        assertThrows(CustomerException.class, () -> customerService.deleteResource(customer, customerWithFile.getFilename()), "El archivo no existe");
        verify(uploadFileService, never()).checkFile(any(Path.class));
        verify(uploadFileService, never()).deleteFile(anyString());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    private void createCustomerServiceWithInvalidFileStorageLocation() {
        new CustomerServiceImpl(new ModelMapper(), customerRepository, uploadFileService, regionService, "/no/existent");
    }

    private byte[] createImageBytes() {
        String imageData = "data:image/png;base64,iVBORw0KG...";
        return imageData.getBytes(StandardCharsets.UTF_8);
    }
}

