package com.turkcellcamp.rentacar.rentacar.business.dtos.creditCardDtos;

import com.turkcellcamp.rentacar.rentacar.business.requests.creditCardRequests.DeleteCreditCardRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCreditCardDto {
	
	private int creditCardId;
	
	private String cardOwnerName;
	
	private String cardNumber;
	
	private int cardCvvNumber;
}
