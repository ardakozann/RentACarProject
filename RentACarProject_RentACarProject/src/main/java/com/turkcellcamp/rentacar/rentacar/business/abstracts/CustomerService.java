package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Customer;

public interface CustomerService  {
	public Customer getByUserId(int id);
}
