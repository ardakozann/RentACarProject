package com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRentalCarByIdDto {
	private int rentalCarId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private int carId;
	private int customerId;
//	private List<String> additionalServiceName;
	private double totalDailyPrice;
	private long rentOdometer;
	private long returnOdometer;
	private LocalDate plannedReturnDate;
}
