package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of {@link InvoiceDetail} for CRUD operations
 */
public interface IInvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
}
