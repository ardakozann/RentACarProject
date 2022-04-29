package com.turkcellcamp.rentacar.rentacar.business.requests.bankRequests;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBankRequest {

	@NotNull
	private int bankId;
}