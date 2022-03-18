package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CreditCardService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.creditCardDtos.ListCreditCardDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.DeleteCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.UpdateCreditCardRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@Service
public class CreditCardManager implements CreditCardService {

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<ListCreditCardDto> getById(int carId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<ListCreditCardDto>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
