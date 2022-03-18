package com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRentalCarRequest {

	@NotNull
	private int rentalCarId;
}
