package com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderedAdditionalServiceDto {
	private int orderedAdditionalId;
	private int rentalCarId;
	private int additionalServiceId;
	private String additionalServiceName;
}