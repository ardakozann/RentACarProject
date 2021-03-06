package com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String companyName;
	
	@NotNull
	private String taxNumber;
}