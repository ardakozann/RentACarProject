package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.CorporateCustomer;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {
	public CorporateCustomer getByEmail(String email);
	public CorporateCustomer getByTaxNumber(String taxNumber);
	public CorporateCustomer getByUserId(int id);
}