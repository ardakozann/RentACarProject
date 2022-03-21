package com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCardByCustomerIdDto {
	
	private String cardOwnerName;
	
	private String cardNumber;
	
	private int cardCvvNumber;
	
	private int userId;
}
