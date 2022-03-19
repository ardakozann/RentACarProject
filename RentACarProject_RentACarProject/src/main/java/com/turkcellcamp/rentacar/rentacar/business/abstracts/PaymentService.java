package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface PaymentService {
	public Result add(CreatePaymentRequest createPaymentRequest);
}
