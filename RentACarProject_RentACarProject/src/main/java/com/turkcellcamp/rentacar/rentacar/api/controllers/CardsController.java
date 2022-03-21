package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CardService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.GetCardByCustomerIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.GetCardByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos.ListCardDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.CreateCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.DeleteCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.UpdateCardRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
	private CardService cardService;

	@Autowired
	public CardsController(CardService cardService) {
		this.cardService = cardService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCardRequest createCardRequest) {
		return this.cardService.add(createCardRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCardRequest updateCardRequest) {
		return this.cardService.update(updateCardRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCardRequest deleteCardRequest) {
		return this.cardService.delete(deleteCardRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCardDto>> getAll() {
		return this.cardService.getAll();
	}
	
	@GetMapping("/getcardbyid")
	public DataResult<GetCardByIdDto> getById(@RequestParam @Valid int cardId) {
		return this.cardService.getCardById(cardId);
	}
	
	@GetMapping("/getcardbycustomerid")
	public DataResult<List<GetCardByCustomerIdDto>> getCardByCustomerId(@RequestParam @Valid int customerId) {
		return this.cardService.getCardByCustomerId(customerId);
	}
	
}
