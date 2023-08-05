package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.controller.request.RequestCreateInvoice;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateInvoice;
import com.springboot.backend.proyecto1.model.Invoice;
import com.springboot.backend.proyecto1.repository.IInvoiceRepository;
import com.springboot.backend.proyecto1.service.ICustomerService;
import com.springboot.backend.proyecto1.service.IInvoiceService;
import com.springboot.backend.proyecto1.exception.InvoiceException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Services for Invoice
 */
@Service
public class InvoiceServiceImpl implements IInvoiceService {

    public final IInvoiceRepository invoiceRepository;
    public final ICustomerService customerService;
    private final ModelMapper modelMapper;

    public InvoiceServiceImpl(ModelMapper modelMapper, IInvoiceRepository invoiceRepository, ICustomerService customerService) {
        this.modelMapper = modelMapper;
        this.invoiceRepository = invoiceRepository;
        this.customerService = customerService;
    }

    /**
     * Implementation method save a Invoice
     *
     * @param requestCreateInvoice - Invoice data to create
     * @return {@link Invoice}
     */
    @Override
    @Transactional
    public Invoice save(RequestCreateInvoice requestCreateInvoice) {
        if (!customerService.exists(requestCreateInvoice.getCustomer().getId()))
            throw new InvoiceException("El cliente registrado en la factura no existe");
        Invoice invoice = mapToEntity(requestCreateInvoice);
        return invoiceRepository.save(invoice);
    }

    /**
     * Implementation method get an Invoice by id
     *
     * @param id to Invoice
     * @return {@link Invoice}
     */
    @Override
    @Transactional(readOnly = true)
    public Invoice findById(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) throw new InvoiceException("La factura no existe");
        return invoice.get();
    }

    /**
     * Implementation method update an Invoice
     *
     * @param currentInvoice - current invoice
     * @param newInvoice     - new invoice
     * @return {@link Invoice}
     */
    @Override
    @Transactional
    public Invoice update(Invoice currentInvoice, RequestUpdateInvoice newInvoice) {
        if (!Objects.equals(currentInvoice.getId(), newInvoice.getId()))
            throw new InvoiceException("Factura no válida para actualizar");

        if (!Objects.equals(currentInvoice.getCustomer().getId(), newInvoice.getCustomer().getId()))
            throw new InvoiceException("No se puede actualizar el cliente en la factura");
        currentInvoice.getDetails().clear();
        currentInvoice.getDetails().addAll(newInvoice.getDetails());
        currentInvoice.setObservation(newInvoice.getObservation());
        currentInvoice.setDescription(newInvoice.getDescription());
        return invoiceRepository.save(currentInvoice);
    }

    /**
     * Implementation method delete an Invoice
     *
     * @param id to Invoice
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) throw new InvoiceException("La factura no existe");
        invoiceRepository.delete(invoice.get());
    }

    /**
     * Method for create Invoice by RequestCreateInvoice
     *
     * @param requestCreateInvoice -> requestCreateInvoice
     * @return Invoice
     */
    private Invoice mapToEntity(RequestCreateInvoice requestCreateInvoice) {
        return modelMapper.map(requestCreateInvoice, Invoice.class);
    }
}
