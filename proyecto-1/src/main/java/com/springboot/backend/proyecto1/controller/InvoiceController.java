package com.springboot.backend.proyecto1.controller;

import com.springboot.backend.proyecto1.controller.request.RequestCreateInvoice;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateInvoice;
import com.springboot.backend.proyecto1.controller.response.ResponseInvoice;
import com.springboot.backend.proyecto1.model.Invoice;
import com.springboot.backend.proyecto1.service.IInvoiceService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for {@link Invoice}
 */
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private static final String INVOICE_CREATED_MESSAGE = "La factura fue creada con éxito";

    private static final String INVOICE_UPDATED_MESSAGE = "La factura fue actualizada con éxito";

    private static final String INVOICE_DELETED_MESSAGE = "La factura fue eliminada con éxito";

    private static final String RESPONSE = "Respuesta: {}";

    private final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    public final IInvoiceService invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * Controller to Create {@link Invoice}
     *
     * @param invoice -> for entity
     * @return {@link Invoice}
     */
    @ApiOperation("Petición para crea una factura")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseInvoice> createInvoice(@Valid @RequestBody RequestCreateInvoice invoice) {
        logger.info("Petición para crear factura {}", invoice);
        Invoice newInvoice = invoiceService.save(invoice);
        ResponseInvoice response = new ResponseInvoice(INVOICE_CREATED_MESSAGE, newInvoice);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Controller to Get {@link Invoice}
     *
     * @param id -> to Invoice
     * @return {@link Invoice}
     */
    @ApiOperation("Petición para obtener una factura")
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        logger.info("Petición para obtener una factura por id {}", id);
        Invoice invoice = invoiceService.findById(id);
        logger.info("Factura {}", invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    /**
     * Controller to Update {@link Invoice}
     */
    @ApiOperation("Petición para actualizar una factura")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseInvoice> updateInvoice(@PathVariable Long id, @RequestBody RequestUpdateInvoice invoice) {
        logger.info("Petición para actualizar una factura {}", invoice);
        Invoice currentInvoice = invoiceService.findById(id);
        Invoice updateInvoice = invoiceService.update(currentInvoice, invoice);
        ResponseInvoice response = new ResponseInvoice(INVOICE_UPDATED_MESSAGE, updateInvoice);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Controller to Delete {@link Invoice}
     *
     * @param id -> to Invoice
     * @return message
     */
    @ApiOperation("Petición para eliminar una factura")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteInvoice(@PathVariable Long id) {
        logger.info("Petición para eliminar una factura con id {}", id);
        invoiceService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", INVOICE_DELETED_MESSAGE);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
