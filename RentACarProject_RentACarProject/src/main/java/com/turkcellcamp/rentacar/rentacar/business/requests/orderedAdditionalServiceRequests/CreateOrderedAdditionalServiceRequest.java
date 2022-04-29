package com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	@Positive
	@NotNull
	private int rentalCarId;
	
	@Positive
	@NotNull
	private int additionalServiceId;
	
	
	
}