package com.turkcellcamp.rentacar.rentacar.business.requests.carRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarRequest {
	
	@NotNull
	private int carId;
}