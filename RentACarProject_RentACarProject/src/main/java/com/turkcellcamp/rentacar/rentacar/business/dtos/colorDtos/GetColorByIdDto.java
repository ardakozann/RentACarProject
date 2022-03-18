package com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetColorByIdDto {
	private int colorId;
	private String colorName;
}
