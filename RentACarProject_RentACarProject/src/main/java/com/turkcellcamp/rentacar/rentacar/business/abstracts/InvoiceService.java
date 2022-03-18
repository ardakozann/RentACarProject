package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.ListInvoiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface InvoiceService {
	
	Result add(CreateInvoiceRequest createInvoiceRequest);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);
	
	Result delete(int id);

	DataResult<List<ListInvoiceDto>> getAll();

	DataResult<GetInvoiceByIdDto> getByInvoiceId(int invoiceId);
	
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (LocalDate startDate, LocalDate finishDate);
	
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id);
}
