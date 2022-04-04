package com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarAccidentDto {
	
	private int carAccidentId;
	
	private String carAccidentDescription;

	private int carId;
}