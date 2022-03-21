package com.turkcellcamp.rentacar.rentacar.business.dtos.cardDtos;

import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.ListCarDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCardDto {
	
	private String cardOwnerName;
	
	private String cardNumber;
	
	private int cardCvvNumber;
	
	private int userId;
}
