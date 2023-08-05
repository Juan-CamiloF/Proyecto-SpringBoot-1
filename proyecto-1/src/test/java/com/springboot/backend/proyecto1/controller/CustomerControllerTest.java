package com.springboot.backend.proyecto1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.backend.proyecto1.controller.request.RequestCreateCustomer;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateCustomer;
import com.springboot.backend.proyecto1.data.CustomerData;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.service.ICustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ICustomerService customerService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCustomer() throws Exception {
        Customer customer = CustomerData.CUSTOMER_1();
        RequestCreateCustomer requestCreateCustomer = new RequestCreateCustomer();
        requestCreateCustomer.setNames(customer.getNames());
        requestCreateCustomer.setSurnames(customer.getSurnames());
        requestCreateCustomer.setEmail(customer.getEmail());
        requestCreateCustomer.setDateOfBirth(customer.getDateOfBirth());
        requestCreateCustomer.setRegion(customer.getRegion());
        when(customerService.save(any())).thenReturn(customer);
        String request = asJsonString(requestCreateCustomer);
        mvc.perform(post("/api/v1/customers")
                        .with(csrf())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("El cliente fue creado con éxito"))
                .andExpect(jsonPath("$.customer.id").value(customer.getId()))
                .andExpect(jsonPath("$.customer.names").value(customer.getNames()))
                .andExpect(jsonPath("$.customer.surnames").value(customer.getSurnames()))
                .andExpect(jsonPath("$.customer.email").value(customer.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCustomers() throws Exception {
        List<Customer> customerList = CustomerData.CUSTOMER_LIST();
        when(customerService.findAll()).thenReturn(customerList);
        mvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCustomerPage() throws Exception {
        Page<Customer> customerPage = CustomerData.CUSTOMER_PAGE();
        when(customerService.findAllPaginated(0, 10)).thenReturn(customerPage);
        mvc.perform(get("/api/v1/customers/page?pageNumber=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCustomersByRegion() throws Exception {
        Page<Customer> customerPage = CustomerData.CUSTOMER_PAGE();
        when(customerService.findAllByRegion(anyLong(), anyInt(), anyInt())).thenReturn(customerPage);
        mvc.perform(get("/api/v1/customers/filter/1?pageNumber=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCustomerById() throws Exception {
        Customer customer = CustomerData.CUSTOMER_1();
        when(customerService.findById(anyLong())).thenReturn(customer);
        mvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.names").value(customer.getNames()))
                .andExpect(jsonPath("$.surnames").value(customer.getSurnames()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateCustomer() throws Exception {
        Customer customer = CustomerData.CUSTOMER_1();
        Customer updatedCustomer = CustomerData.CUSTOMER_1_UPDATE_NAME();
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(customerService.update(any(), any())).thenReturn(updatedCustomer);
        RequestUpdateCustomer requestUpdateCustomer = getRequestUpdateCustomer(customer);
        String request = asJsonString(requestUpdateCustomer);
        mvc.perform(put("/api/v1/customers/1")
                        .with(csrf())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El cliente fue actualizado con éxito"))
                .andExpect(jsonPath("$.customer.id").value(updatedCustomer.getId()))
                .andExpect(jsonPath("$.customer.names").value(updatedCustomer.getNames()))
                .andExpect(jsonPath("$.customer.surnames").value(updatedCustomer.getSurnames()))
                .andExpect(jsonPath("$.customer.email").value(updatedCustomer.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteCustomer() throws Exception {
        mvc.perform(delete("/api/v1/customers/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("El cliente fue eliminado con éxito"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUploadFilename() throws Exception {
        Customer customer = CustomerData.CUSTOMER_1();
        Customer updateCustomer = CustomerData.CUSTOMER_1_WITH_FILE();
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(customerService.uploadResource(any(), any())).thenReturn(updateCustomer);
        byte[] imageBytes = createImageBytes();
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", imageBytes);
        mvc.perform(multipart("/api/v1/customers/upload")
                        .file(file)
                        .param("id", String.valueOf(1))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El archivo se ha subido correctamente"))
                .andExpect(jsonPath("$.customer.filename").value(updateCustomer.getFilename()))
                .andExpect(jsonPath("$.customer.urlFilename").isNotEmpty());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteResourceCustomer() throws Exception {
        Customer customer = CustomerData.CUSTOMER_1_WITH_FILE();
        Customer updateCustomer = CustomerData.CUSTOMER_1();
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(customerService.deleteResource(any(), anyString())).thenReturn(updateCustomer);
        mvc.perform(delete("/api/v1/customers/delete/1/filename")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Archivo eliminado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFilePng() throws Exception {
        Resource resource = new ByteArrayResource(createImageBytes());
        when(customerService.getResource(anyString())).thenReturn(resource);
        mvc.perform(get("/api/v1/customers/images/image.png"))
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE))
                .andExpect(content().bytes(createImageBytes()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFileJpg() throws Exception {
        Resource resource = new ByteArrayResource(createImageBytes());
        when(customerService.getResource(anyString())).thenReturn(resource);
        mvc.perform(get("/api/v1/customers/images/image.jpg"))
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(content().bytes(createImageBytes()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFileJpeg() throws Exception {
        Resource resource = new ByteArrayResource(createImageBytes());
        when(customerService.getResource(anyString())).thenReturn(resource);
        mvc.perform(get("/api/v1/customers/images/image.jpeg"))
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(content().bytes(createImageBytes()));
    }


    private static RequestUpdateCustomer getRequestUpdateCustomer(Customer customer) {
        RequestUpdateCustomer requestUpdateCustomer = new RequestUpdateCustomer();
        requestUpdateCustomer.setId(customer.getId());
        requestUpdateCustomer.setNames(customer.getNames());
        requestUpdateCustomer.setSurnames(customer.getSurnames());
        requestUpdateCustomer.setEmail(customer.getEmail());
        requestUpdateCustomer.setDateOfBirth(customer.getDateOfBirth());
        requestUpdateCustomer.setRegion(customer.getRegion());
        requestUpdateCustomer.setCreatedAt(customer.getCreatedAt());
        requestUpdateCustomer.setUpdateAt(customer.getUpdateAt());
        return requestUpdateCustomer;
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(obj);
    }

    private byte[] createImageBytes() {
        String imageData = "data:image/png;base64,iVBORw0KG...";
        return imageData.getBytes(StandardCharsets.UTF_8);
    }

}
