package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.GetCardByCustomerIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.GetCardByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.ListCardDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.CreateCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.DeleteCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.UpdateCardRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface CardService {
	
	Result add(CreateCardRequest createCardRequest);
	Result update(UpdateCardRequest updateCardRequest);
	Result delete(DeleteCardRequest deleteCardRequest);
	DataResult<List<ListCardDto>> getAll();
	DataResult<GetCardByIdDto> getCardById(int cardId);
	DataResult<List<GetCardByCustomerIdDto>> getCardByCustomerId(int customerId);
}