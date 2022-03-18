package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{
	Invoice getByInvoiceId(int id);
	List<Invoice> getByCustomer_UserId(int id);
	List<Invoice> findByCreateDateBetween(LocalDate startDate, LocalDate finishDate);
	Invoice getByInvoiceNo(long invoiceNo);
}
