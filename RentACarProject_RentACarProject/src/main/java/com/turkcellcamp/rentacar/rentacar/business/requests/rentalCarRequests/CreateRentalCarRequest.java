package com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarRequest {
	
	@NotNull
	@FutureOrPresent
	private LocalDate rentDate;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	@Positive
	private int userId;
	
	@NotNull
	@Positive
	private int rentCityId;
	
	@NotNull
	@Positive
	private int returnCityId;
	
	@NotNull
	@FutureOrPresent
	private LocalDate plannedReturnDate;
}