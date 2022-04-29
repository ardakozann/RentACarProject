package com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAdditionalServiceDto {
	private int additionalServiceId;
	private String additionalServiceName;
	private double additionalServiceDailyPrice;
}