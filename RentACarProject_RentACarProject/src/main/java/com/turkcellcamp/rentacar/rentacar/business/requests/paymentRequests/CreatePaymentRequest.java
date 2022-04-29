package com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests;

import javax.validation.constraints.NotNull;

import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequestForTransactional;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequestForTransactional;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.CreateRentalCarRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
//	@NotNull
//	private int invoiceId;
//	
//	@NotNull
//	private int orderedAdditionalServiceId;
//	
//	@NotNull
//	private String cardOwnerName;
//	
//	@NotNull
//	private String cardNumber;
//	
//	@NotNull
//	private int cardCvvNumber;
	
	private CreateRentalCarRequest createRentalCarRequest;
	
	private CreateOrderedAdditionalServiceRequestForTransactional createOrderedAdditionalServiceRequestForTransactional;
	
	private CreateInvoiceRequestForTransactional createInvoiceRequestForTransactional;
	
	@NotNull
	private String cardOwnerName;
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private int cardCvvNumber;
	
	private boolean saveCard;
	
	
}