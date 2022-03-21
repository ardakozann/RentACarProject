package com.turkcellcamp.rentacar.rentacar.business.adapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.rentacar.business.outServices.FakeIsBankManager;

@Service
@Primary
public class FakeIsBankServiceAdapter implements PosService {

	@Override
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		
		FakeIsBankManager fakeIsBankManager = new FakeIsBankManager();
		
		fakeIsBankManager.makePayment(cardOwnerName, cardNumber, cardCvvNumber);
		
		return true;
	}

}
