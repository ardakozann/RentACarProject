package com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {
	
	@NotNull
	private String email;
	
}
