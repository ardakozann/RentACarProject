package com.turkcellcamp.rentacar.rentacar.business.requests.bankRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBankRequest {
	
	@NotNull
	@Positive
	private int bankNo;
	
	@NotNull
	private String bankName;
}
