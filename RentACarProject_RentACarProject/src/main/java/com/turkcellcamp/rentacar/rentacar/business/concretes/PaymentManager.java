package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.PaymentDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	PosService posService;
	ModelMapperService modelMapperService;
	PaymentDao paymentDao;
	
	@Autowired
	public PaymentManager(@Lazy PosService posService, ModelMapperService modelMapperService, PaymentDao paymentDao) {
		this.posService = posService;
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
	}

	@Override
	@Transactional
	public Result add(CreatePaymentRequest createPaymentRequest) {
	
		
		
		toSendPosService(createPaymentRequest);
	
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult("Ödeme başarılı..");
		
	}
	
	private void toSendPosService(CreatePaymentRequest createPaymentRequest) {
		this.posService.payments(createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber(), createPaymentRequest.getCardCvvNumber());
	}

}
