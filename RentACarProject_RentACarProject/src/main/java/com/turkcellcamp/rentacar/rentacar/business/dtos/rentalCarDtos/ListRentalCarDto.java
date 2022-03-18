package com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos;

import java.time.LocalDate;
import java.util.List;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.OrderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRentalCarDto {
	private int rentalCarId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private int carId;
	private int customerId;
//	private List<OrderedAdditionalService> orderedAdditionalService;
	private double totalDailyPrice;
	private long rentOdometer;
	private long returnOdometer;
}
