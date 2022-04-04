package com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBrandByIdDto {
	private int brandId;
	private String brandName;
}