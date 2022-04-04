package com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCardRequest {
	
	@NotNull
	private int cardId;
}