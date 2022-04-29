package com.turkcellcamp.rentacar.rentacar.business.dtos.cityDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCityDto {
	private int cityId;
	private String cityName;
}