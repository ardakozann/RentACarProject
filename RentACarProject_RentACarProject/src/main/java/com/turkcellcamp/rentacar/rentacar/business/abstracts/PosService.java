package com.turkcellcamp.rentacar.rentacar.business.abstracts;

public interface PosService {
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber);
}