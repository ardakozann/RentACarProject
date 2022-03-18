package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.creditCardDtos.ListCreditCardDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.DeleteCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.UpdateCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface CreditCardService {
	
	Result add(CreateCreditCardRequest createCreditCardRequest);
	
	Result update(UpdateCreditCardRequest updateCreditCardRequest);
	
	Result delete(DeleteCreditCardRequest deleteCreditCardRequest);
	
	DataResult <ListCreditCardDto> getById(int carId);
	
	DataResult <List<ListCreditCardDto>> getAll();
	
}
