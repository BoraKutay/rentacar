package com.turkcell.rentacar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Invoice;
//jdbcRepository
//ai
@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
	List<Invoice> getAllByCustomer_CustomerId(int customerId);
	List<Invoice> getAllByBillingDateBetween(LocalDate startDate, LocalDate endDate);
}
