package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.CreditCard;

@Repository
public interface CreditCardDao {
	public CreditCard getByCreditCardId (int creditCardId);
}
