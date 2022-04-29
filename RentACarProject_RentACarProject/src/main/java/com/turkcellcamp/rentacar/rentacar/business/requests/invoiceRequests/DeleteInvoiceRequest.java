package com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteInvoiceRequest {
	
	@NotNull
	@Positive
	private int invoiceId;
}