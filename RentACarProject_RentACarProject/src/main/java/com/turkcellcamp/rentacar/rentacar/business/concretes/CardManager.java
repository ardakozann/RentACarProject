package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CardService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.GetCardByCustomerIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.GetCardByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.ListCardDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.CreateCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.DeleteCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.UpdateCardRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CardDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Card;

@Service
public class CardManager implements CardService {

	private ModelMapperService modelMapperService;
	private CardDao cardDao;
	private CustomerService customerService;
	private UserService userService;
	
	@Autowired
	public CardManager(ModelMapperService modelMapperService, CardDao cardDao, CustomerService customerService,
			UserService userService) {
		this.modelMapperService = modelMapperService;
		this.cardDao = cardDao;
		this.customerService = customerService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateCardRequest createCardRequest) {
		
		checkIfExistCustomer(createCardRequest.getUserId());
		
		Card card = this.modelMapperService.forRequest().map(createCardRequest, Card.class);
		card.setCustomer(this.customerService.getByUserId(createCardRequest.getUserId()));
		card.setCardId(0);
		
		checkIfExistCard(card);
		
		this.cardDao.save(card);
		
		return new SuccessResult(BusinessMessage.CARDSERVICE_ADD);
	}

	@Override
	public Result update(UpdateCardRequest updateCardRequest) {
		
		Card card = this.modelMapperService.forRequest().map(updateCardRequest, Card.class);
		
		checkIfExistCard(card);
		
		this.cardDao.save(card);		
		
		return new SuccessResult(BusinessMessage.CARDSERVICE_UPDATE);
	}

	@Override
	public Result delete(DeleteCardRequest deleteCardRequest) {
		
		checkIfExistById(deleteCardRequest.getCardId());
		
		this.cardDao.deleteById(deleteCardRequest.getCardId());
		
		return new SuccessResult(BusinessMessage.CARDSERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListCardDto>> getAll() {
		
		List<Card> result = this.cardDao.findAll();
		List<ListCardDto> response = result.stream().map(card->this.modelMapperService.forDto()
				.map(card, ListCardDto.class)).collect(Collectors.toList());
		//response = toSetforGetAll(response, result);
		
		return new SuccessDataResult<List<ListCardDto>>(response);
	}

	@Override
	public DataResult<GetCardByIdDto> getCardById(int cardId) {
		
		checkIfExistById(cardId);
		
		Card result = this.cardDao.getByCardId(cardId);
		GetCardByIdDto response = this.modelMapperService.forDto().map(result, GetCardByIdDto.class);
		
		return new SuccessDataResult<GetCardByIdDto>(response);
	}

	@Override
	public DataResult<List<GetCardByCustomerIdDto>> getCardByCustomerId(int customerId) {
		
		checkIfExistCustomer(customerId);
		
		List<Card> result = this.cardDao.getByCustomer_UserId(customerId);
		List<GetCardByCustomerIdDto> response = result.stream().map(card->this.modelMapperService.forDto()
				.map(card, GetCardByCustomerIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCardByCustomerIdDto>>(response);
	}

	
//	private List<ListCardDto> toSetforGetAll(List<ListCardDto> response, List<Card> result){
//		for(int i = 0 ; i < response.size(); i++) {
//			response.get(i).
//		}
//		return response;
//	}
	
	private boolean checkIfExistCard (Card card) {
		Card result = this.cardDao.getByCardOwnerNameAndCardNumberAndCardCvvNumber(card.getCardOwnerName(), card.getCardNumber(), card.getCardCvvNumber());
		if (result != null) {
			throw new BusinessException(BusinessMessage.CARDSERVICE_CHECKIFEXISTCARD_ERROR);
		}
		return true;
	}
	
	private boolean checkIfExistCustomer(int userId) {
		this.customerService.checkIfExistById(userId);
		return true;
	}
	
	private boolean checkIfExistById(int cardId) {
		Card result = this.cardDao.getByCardId(cardId);
		if(result == null) {
			throw new BusinessException(BusinessMessage.CARDSERVICE_CHECKIFEXISTBYID_ERROR);
		}
		return true;
	}
}