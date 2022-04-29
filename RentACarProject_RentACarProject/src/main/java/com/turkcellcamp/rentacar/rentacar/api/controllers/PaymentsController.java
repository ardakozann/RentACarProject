package com.turkcellcamp.rentacar.rentacar.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentAdditionalRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	
	PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
		return this.paymentService.add(createPaymentRequest);
	}
	
	@PostMapping("/addadditional")
	public Result addAdditional(@RequestBody @Valid CreatePaymentAdditionalRequest createPaymentAdditionalRequest) {
		return this.paymentService.addForAdditionalTime(createPaymentAdditionalRequest);
	}
	
}