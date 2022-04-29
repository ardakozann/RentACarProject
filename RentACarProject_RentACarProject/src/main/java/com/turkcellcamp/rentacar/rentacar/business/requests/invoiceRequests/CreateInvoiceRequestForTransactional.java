package com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceRequestForTransactional {
	
	@NotNull
	@Positive
	private long invoiceNo;
	
	@Positive
	@NotNull
	private int userId;
}
