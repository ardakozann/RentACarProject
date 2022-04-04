package com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarAccidentRequest {
	
	@NotNull
	private String carAccidentDescription;

	@NotNull
	@Positive
	private int carId;
}