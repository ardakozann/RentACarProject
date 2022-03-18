package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Customer;

public interface CustomerDao extends JpaRepository <Customer, Integer> {
	public Customer getByUserId(int id);
}
