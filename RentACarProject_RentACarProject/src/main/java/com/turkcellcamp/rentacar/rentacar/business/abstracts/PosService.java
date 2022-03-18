package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface PosService {
	Result add(CreateCreditCardRequest createCreditCardRequest);

}
