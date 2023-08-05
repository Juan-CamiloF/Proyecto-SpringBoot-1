package com.springboot.backend.proyecto1.controller;

import com.springboot.backend.proyecto1.controller.request.RequestCreateCustomer;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateCustomer;
import com.springboot.backend.proyecto1.controller.response.ResponseCustomer;
import com.springboot.backend.proyecto1.service.ICustomerService;
import com.springboot.backend.proyecto1.model.Customer;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

/**
 * Controller for {@link Customer}
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final String CUSTOMER_CREATED_MESSAGE = "El cliente fue creado con éxito";

    private static final String CUSTOMER_UPDATED_MESSAGE = "El cliente fue actualizado con éxito";

    private static final String UPLOAD_FILE_TO_SERVER = "El archivo se ha subido correctamente";

    private static final String DELETE_FILE_TO_SERVER = "Archivo eliminado";

    private static final String RESPONSE = "Respuesta: {}";

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    /**
     * Controller to Create {@link Customer}
     *
     * @param customer -> for entity
     * @return {@link Customer}
     */
    @ApiOperation("Petición para crear un cliente")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseCustomer> createCustomer(@Valid @RequestBody RequestCreateCustomer customer) {
        logger.info("Petición para crear cliente {}", customer);
        Customer newCustomer = customerService.save(customer);
        ResponseCustomer response = new ResponseCustomer(CUSTOMER_CREATED_MESSAGE, newCustomer);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Controller to List {@link Customer}
     *
     * @return List to {@link Customer}
     */
    @ApiOperation("Petición para listar todos los clientes")
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        logger.info("Petición para obtener todos los clientes");
        List<Customer> customers = customerService.findAll();
        logger.info("Clientes obtenidos {}", customers.size());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Controller to Paginated List to {@link Customer}
     *
     * @return Page to {@link Customer}
     */
    @ApiOperation("Petición para obtener página de clientes")
    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> getCustomerPage(@RequestParam("pageNumber") Integer pageNumber,
                                                          @RequestParam("pageSize") Integer pageSize) {
        logger.info("Petición para obtener página de clientes tamaño {}, número {}", pageSize, pageNumber);
        Page<Customer> customers = customerService.findAllPaginated(pageNumber, pageSize);
        logger.info("Clientes obtenidos {} en {} páginas", customers.getTotalElements(), customers.getTotalPages());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Controller to List filtered by Customer Region
     *
     * @return Listo to {@link Cliente}
     */
    @ApiOperation("Petición para obtener página de clientes por región")
    @GetMapping("/filter/{region}")
    public ResponseEntity<Page<Customer>> getCustomersByRegion(@PathVariable("region") Long id,
                                                                 @RequestParam("pageNumber") Integer pageNumber,
                                                                 @RequestParam("pageSize") Integer pageSize) {
        logger.info("Petición para obtener página de clientes tamaño {}, número {} en la región {}", pageSize, pageNumber, id);
        Page<Customer> customers = customerService.findAllByRegion(id, pageNumber, pageSize);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Controller to Get a {@link Customer}
     *
     * @param id -> to Customer
     * @return {@link Customer}
     */
    @ApiOperation("Petición para obtener un cliente por id")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        logger.info("Petición para obtener cliente por id {}", id);
        Customer customer = customerService.findById(id);
        logger.info("Cliente {}", customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Controller to Update a {@link Customer}
     *
     * @param cliente -> to Update
     * @param id      -> to Customer
     * @return {@link Customer}
     */
    @ApiOperation("Petición para actualizar un cliente")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseCustomer> updateCustomer(@Valid @RequestBody RequestUpdateCustomer customer, @PathVariable Long id) {
        logger.info("Petición para actualizar un cliente {} con id {}", customer, id);
        Customer currentCustomer = customerService.findById(id);
        Customer updateCustomer = customerService.update(currentCustomer, customer);
        ResponseCustomer response = new ResponseCustomer(CUSTOMER_UPDATED_MESSAGE, updateCustomer);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Controller to Delete a {@link Customer}
     *
     * @param id -> to Customer
     * @return message
     */
    @ApiOperation("Petición para eliminar un cliente")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Long id) {
        logger.info("Petición para eliminar un cliente con id {}", id);
        Map<String, String> response = new HashMap<>();
        customerService.delete(id);
        response.put("message", "El cliente fue eliminado con éxito");
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Controller to Upload file by {@link Cliente}
     *
     * @param file -> Customer filename
     * @return message
     */
    @ApiOperation("Petición para cargar un archivo a un cliente")
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseCustomer> uploadFilename(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        logger.info("Petición para cargar una foto {} al cliente con id {}", file.getOriginalFilename(), id);
        Customer customer = customerService.findById(id);
        Customer updateCustomer = customerService.uploadResource(customer, file);
        ResponseCustomer response = new ResponseCustomer(UPLOAD_FILE_TO_SERVER, updateCustomer);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Controller to Get a file by {@link Customer}
     *
     * @param fileName -> Customer filename
     * @return resource
     */
    @ApiOperation("Petición para obtener un archivo de un cliente")
    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> getFilename(@PathVariable String fileName) {
        logger.info("Petición para obtener una foto {}", fileName);
        Resource resource = customerService.getResource(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        MediaType mediaType;
        if (extension.equals("jpg") || extension.equals("jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else {
            mediaType = MediaType.IMAGE_PNG;
        }
        logger.info("Foto {}", resource.getFilename());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Controller to Delete file by {@link Customer}
     *
     * @param fileName -> Customer filename
     * @return message
     */
    @ApiOperation("Petición para eliminar un archivo de un cliente")
    @DeleteMapping("/delete/{id}/{fileName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseCustomer> deleteFilename(@PathVariable Long id, @PathVariable String fileName) {
        logger.info("Petición para eliminar una foto {} de un cliente {}", fileName, id);
        Customer customer = customerService.findById(id);
        Customer updateCustomer = customerService.deleteResource(customer, fileName);
        ResponseCustomer response = new ResponseCustomer(DELETE_FILE_TO_SERVER, updateCustomer);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
