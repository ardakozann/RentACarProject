package com.turkcellcamp.rentacar.rentacar.business.requests.carRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	@NotNull
	private int carId;
	
	@NotNull
	private double dailyPrice;
	
	@NotNull
	private int modelYear;
	
	@NotNull
	private String description;
	
	@NotNull
	private int brandId;
	
	@NotNull
	private int colorId;
}