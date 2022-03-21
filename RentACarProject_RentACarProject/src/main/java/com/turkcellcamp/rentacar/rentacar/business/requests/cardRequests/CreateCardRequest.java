package com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {
	
	@NotNull
	private String cardOwnerName;
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private int cardCvvNumber;
	
	@NotNull
	private int userId;

}
