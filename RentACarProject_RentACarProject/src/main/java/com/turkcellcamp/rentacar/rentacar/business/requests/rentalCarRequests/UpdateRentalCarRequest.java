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
public class UpdateRentalCarRequest {
	
	@NotNull
	@Positive
	private int rentalCarId;
	
	@NotNull
	@FutureOrPresent
	private LocalDate returnDate;
	
	@NotNull
	@Positive
	private int returnCityId;
	
	@NotNull
	private long returnOdometer;
}
