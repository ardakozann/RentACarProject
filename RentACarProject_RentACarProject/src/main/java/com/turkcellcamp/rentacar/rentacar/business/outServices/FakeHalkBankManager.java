package com.turkcellcamp.rentacar.rentacar.business.outServices;

import org.springframework.stereotype.Service;


public class FakeHalkBankManager {
	public boolean makePayment(String cardNumber, String cardOwnerName, int cardCvvNumber) {
		return true;
	}
}
