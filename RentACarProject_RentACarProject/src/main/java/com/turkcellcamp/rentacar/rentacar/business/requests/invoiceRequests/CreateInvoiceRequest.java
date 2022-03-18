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
public class CreateInvoiceRequest {
	
	@NotNull
	@Positive
	private long invoiceNo;
	
	@FutureOrPresent
	@NotNull
	private LocalDate createDate;
	
	@Positive
	@NotNull
	private int userId;
	
	@Positive
	@NotNull
	private int rentalCarId;

}