package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.FakeHalkBankPosService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.FakeIsBankPosService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentRequest;

@Service
public class PosAdapter implements PosService {

	PaymentService paymentService;
	FakeHalkBankPosService fakeHalkBankPosService;
	FakeIsBankPosService fakeIsBankPosService;
	
	@Autowired
	public PosAdapter(@Lazy PaymentService paymentService, FakeHalkBankPosService fakeHalkBankPosService,
			FakeIsBankPosService fakeIsBankPosService) {
		this.paymentService = paymentService;
		this.fakeHalkBankPosService = fakeHalkBankPosService;
		this.fakeIsBankPosService = fakeIsBankPosService;
	}

	@Override
	public void payment(CreatePaymentRequest createPaymentRequest) {

		String last4digits = createPaymentRequest.getCardNumber().substring(12);
		
		if(last4digits.equals("5002")) {
			fakeHalkBankPosService.fakeHalkBankService(createPaymentRequest.getCardCvvNumber(),
					createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber());
		}
		else if (last4digits.equals("5003")) {
			fakeIsBankPosService.fakeIsBankService(createPaymentRequest.getCardOwnerName(),
					createPaymentRequest.getCardNumber(), createPaymentRequest.getCardCvvNumber());
		}
	}


}
