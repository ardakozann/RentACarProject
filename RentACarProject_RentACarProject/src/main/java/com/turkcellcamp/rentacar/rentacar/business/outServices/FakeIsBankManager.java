package com.turkcellcamp.rentacar.rentacar.business.outServices;

import org.springframework.stereotype.Service;

@Service
public class FakeIsBankManager {
	public boolean makePayment(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		return true;
	}
}