package com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	@NotNull
	@Size(min=2)
	private String description;
	
	@NotNull
	private int carId;
	
	@NotNull
	@FutureOrPresent
	private LocalDate maintenanceDate;
}
