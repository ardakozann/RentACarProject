package com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests;

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
public class UpdateInvoiceRequest {
	
	@NotNull
	@Positive
	private int invoiceId;
	
	@FutureOrPresent
	@NotNull
	private LocalDate createDate;
}
