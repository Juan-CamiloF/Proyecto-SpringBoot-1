package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of {@link Invoice} for CRUD operations
 */
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
}
