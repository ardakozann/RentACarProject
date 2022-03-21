package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentRequest;

public interface PosService {
//	public void fakeIsBankService(String fullName, String cardNo, int Cvv);
//	public void fakeHalkBankService(int Cvv, String fullName, String cardNo);
	public boolean payments(String cardOwnerName, String cardNumber, int cardCvvNumber);
}
