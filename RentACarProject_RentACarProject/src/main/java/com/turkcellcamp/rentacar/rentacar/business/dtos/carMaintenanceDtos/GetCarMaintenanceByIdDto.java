package com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceByIdDto {
	private int carMaintenanceId;
	private String description;
	private LocalDate returnDate;
	private int carId;
	private String brandName;
	private String colorName;
}
