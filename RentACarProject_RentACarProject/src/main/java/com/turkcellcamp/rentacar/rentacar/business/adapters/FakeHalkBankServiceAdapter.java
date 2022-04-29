package com.turkcellcamp.rentacar.rentacar.business.adapters;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.rentacar.business.outServices.FakeHalkBankManager;

@Service
public class FakeHalkBankServiceAdapter implements PosService{

	@Override
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		
		FakeHalkBankManager fakeHalkBankManager = new FakeHalkBankManager();
		
		fakeHalkBankManager.makePayment(cardNumber, cardOwnerName, cardCvvNumber);
		
		return true;
	}

}