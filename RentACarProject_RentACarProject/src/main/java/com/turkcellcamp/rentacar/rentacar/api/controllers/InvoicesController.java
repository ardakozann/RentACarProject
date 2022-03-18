package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.ListInvoiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;


@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;
	
	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest){
		return this.invoiceService.add(createInvoiceRequest);
	}
	
	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest){
		return this.invoiceService.update(updateInvoiceRequest);
	}
	
	@DeleteMapping("/delete")
	Result delete(@RequestParam("InvoiceId") int id) {
		return this.invoiceService.delete(id);
	}
	
	@GetMapping("/getall")
	DataResult<List<ListInvoiceDto>> getAll(){
		return this.invoiceService.getAll();
	}

	@GetMapping("/getbyid")
	DataResult<GetInvoiceByIdDto> getByInvoiceId(@RequestParam("InvoiceId") int invoiceId){
		return this.invoiceService.getByInvoiceId(invoiceId);
	}
	
	@GetMapping("/getbydate")
	DataResult<List<ListInvoiceDto>> getByDateOfBetween (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finishDate){
		return this.invoiceService.getByDateOfBetween(startDate, finishDate);
	}
	
	@GetMapping("/getbycustomer")
	DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(@RequestParam int id) {
		return this.invoiceService.getInvoiceByCustomer(id);
	}
}
