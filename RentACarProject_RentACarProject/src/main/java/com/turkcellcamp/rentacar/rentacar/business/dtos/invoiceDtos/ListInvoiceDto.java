package com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos;



import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListInvoiceDto {
	
	private int invoiceId;
	private long invoiceNo;
	private LocalDate createDate;
	private LocalDate rentDate;
	private LocalDate renturnDate;
	private long numberDays;
	private double rentTotalPrice;
	private int userId;
	private int rentalCarId;
}
