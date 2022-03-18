package com.turkcellcamp.rentacar.rentacar.business.requests.carRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	private double dailyPrice;
	private int modelYear;
	private String description;
	private int brandId;
	private int colorId;
}
