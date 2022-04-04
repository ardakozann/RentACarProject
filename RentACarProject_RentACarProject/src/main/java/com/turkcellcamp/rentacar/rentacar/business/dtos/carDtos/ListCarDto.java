package com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCarDto {
	private int carId;
	private String brandName;
	private String colorName;
	private double dailyPrice;
	private int modelYear;
	private String description;
//	private List<CarMaintenance> carMaintenance;
//	private List<RentalCar> rentalCar;
	private long odometer;
}