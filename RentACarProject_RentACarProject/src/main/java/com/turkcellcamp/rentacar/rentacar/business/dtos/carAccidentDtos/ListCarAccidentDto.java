package com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos;

import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.CreateCarAccidentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarAccidentDto {
	
	private int carAccidentId;
	
	private String carAccidentDescription;

	private int carId;
}
