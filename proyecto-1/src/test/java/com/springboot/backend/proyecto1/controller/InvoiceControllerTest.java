package com.springboot.backend.proyecto1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.backend.proyecto1.controller.request.RequestCreateInvoice;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateInvoice;
import com.springboot.backend.proyecto1.data.CustomerData;
import com.springboot.backend.proyecto1.data.InvoiceData;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.Invoice;
import com.springboot.backend.proyecto1.service.IInvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    IInvoiceService invoiceService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateAnInvoice() throws Exception {
        Customer customer = CustomerData.CUSTOMER_1();
        Invoice invoice = InvoiceData.INVOICE_1();
        when(invoiceService.save(any())).thenReturn(invoice);
        RequestCreateInvoice requestCreateInvoice = new RequestCreateInvoice();
        requestCreateInvoice.setCustomer(customer);
        requestCreateInvoice.setObservation(invoice.getObservation());
        requestCreateInvoice.setDescription(invoice.getDescription());
        requestCreateInvoice.setDetails(invoice.getDetails());
        String request = asJsonString(requestCreateInvoice);
        mvc.perform(post("/api/v1/invoices")
                        .with(csrf())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("La factura fue creada con éxito"))
                .andExpect(jsonPath("$.invoice").isNotEmpty())
                .andExpect(jsonPath("$.invoice.id").value(invoice.getId()))
                .andExpect(jsonPath("$.invoice.observation").value(invoice.getObservation()))
                .andExpect(jsonPath("$.invoice.details").isArray())
                .andExpect(jsonPath("$.invoice.description").value(invoice.getDescription()))
                .andExpect(jsonPath("$.invoice.customer").isNotEmpty())
                .andExpect(jsonPath("$.invoice.customer.id").value(customer.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetInvoice() throws Exception {
        Invoice invoice = InvoiceData.INVOICE_1();
        when(invoiceService.findById(anyLong())).thenReturn(invoice);
        mvc.perform(get("/api/v1/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(invoice.getId()))
                .andExpect(jsonPath("$.observation").value(invoice.getObservation()))
                .andExpect(jsonPath("$.description").value(invoice.getDescription()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateInvoice() throws Exception {
        Invoice invoice = InvoiceData.INVOICE_1();
        Invoice updateInvoice = InvoiceData.INVOICE_UPDATE_1();
        when(invoiceService.findById(anyLong())).thenReturn(invoice);
        when(invoiceService.update(any(), any())).thenReturn(updateInvoice);
        RequestUpdateInvoice requestUpdateInvoice = new RequestUpdateInvoice();
        requestUpdateInvoice.setId(invoice.getId());
        requestUpdateInvoice.setObservation(updateInvoice.getObservation());
        requestUpdateInvoice.setDescription(updateInvoice.getDescription());
        requestUpdateInvoice.setDetails(invoice.getDetails());
        requestUpdateInvoice.setCustomer(invoice.getCustomer());
        requestUpdateInvoice.setCreatedAt(invoice.getCreatedAt());
        String request = asJsonString(requestUpdateInvoice);
        mvc.perform(put("/api/v1/invoices/1")
                        .with(csrf())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("La factura fue actualizada con éxito"))
                .andExpect(jsonPath("$.invoice").isNotEmpty())
                .andExpect(jsonPath("$.invoice.id").value(invoice.getId()))
                .andExpect(jsonPath("$.invoice.description").value(updateInvoice.getDescription()))
                .andExpect(jsonPath("$.invoice.observation").value(updateInvoice.getObservation()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteInvoice() throws Exception {
        mvc.perform(delete("/api/v1/invoices/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("La factura fue eliminada con éxito"));
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(obj);
    }
}
