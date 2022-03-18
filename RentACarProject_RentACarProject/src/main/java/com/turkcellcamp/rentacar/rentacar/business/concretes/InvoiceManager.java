package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.ListInvoiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.InvoiceDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	private RentalCarService rentalCarService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, CustomerService customerService,
			RentalCarService rentalCarService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.rentalCarService = rentalCarService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		
		checkIfNotExistInvoiceByInvoiceNo(createInvoiceRequest.getInvoiceNo());
		checkIfCreateDateAfterReturnDate(createInvoiceRequest.getCreateDate(), createInvoiceRequest.getRentalCarId());
		checkIfExistCustomer(createInvoiceRequest.getUserId());
		checkIfExistRentalCar(createInvoiceRequest.getRentalCarId());
		//aynı rentalCar ın bir daha faturası eklenmemeli
		
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		invoice = toSetForAddMethod(invoice, createInvoiceRequest.getRentalCarId(), createInvoiceRequest.getUserId());
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added");
	}
	
	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		checkIfExistInvoiceId(updateInvoiceRequest.getInvoiceId());
		checkIfCreateDateAfterReturnDate(updateInvoiceRequest.getCreateDate(),
				this.invoiceDao.getByInvoiceId(updateInvoiceRequest.getInvoiceId()).getRentalCar().getRentalCarId());
		
		Invoice invoice = this.invoiceDao.getByInvoiceId(updateInvoiceRequest.getInvoiceId());
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Updated");
	}

	@Override
	public Result delete(int id) {
		
		checkIfExistInvoiceId(id);
		
		this.invoiceDao.deleteById(id);
		
		return new SuccessResult("Invoice.Deleted");
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto()
				.map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		response = toSetReturnDateForGetAllMethod(result, response);
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<GetInvoiceByIdDto> getByInvoiceId(int invoiceId) {
		
		checkIfExistInvoiceId(invoiceId);
		
		var result = this.invoiceDao.getByInvoiceId(invoiceId);
		GetInvoiceByIdDto response = this.modelMapperService.forDto().map(result, GetInvoiceByIdDto.class);
		
		return new SuccessDataResult<GetInvoiceByIdDto>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		
		var result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().
				map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {

		checkIfExistCustomer(id);
		
		var result = this.invoiceDao.getByCustomer_UserId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().
				map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	
	private Invoice toSetForAddMethod(Invoice invoice, int rentalCarId, int userId) {
		var rentalCar = this.rentalCarService.getRentalCarById(rentalCarId).getData();
		var customer = this.customerService.getByUserId(userId);
		
		invoice.setRentDate(rentalCar.getRentDate());
		invoice.setReturnDate(rentalCar.getReturnDate());
		invoice.setNumberDays(ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate()));
		invoice.setRentTotalPrice(rentalCar.getTotalDailyPrice());
		invoice.setCustomer(customer);
		
		return invoice;
	}
	
	private List<ListInvoiceDto> toSetReturnDateForGetAllMethod(List<Invoice> result, List<ListInvoiceDto> response){
		for(int i = 0; i < response.size() ; i++) {
			response.get(i).setRenturnDate(result.get(i).getReturnDate());
			response.get(i).setRentalCarId(result.get(i).getRentalCar().getRentalCarId());		}
		return response;
	}
	
	private boolean checkIfNotExistInvoiceByInvoiceNo(long invoiceNo) {
		var result = this.invoiceDao.getByInvoiceNo(invoiceNo);
		if(result != null) {
			throw new BusinessException("This invoice no already exist.");
		}
		return true;
	}
	
	private boolean checkIfCreateDateAfterReturnDate(LocalDate createDate, int rentalCarId) {
		LocalDate returnDate = this.rentalCarService.getRentalCarById(rentalCarId).getData().getReturnDate();
		if(createDate.isAfter(returnDate) && createDate.isEqual(returnDate)) {
			return true;
		}
		throw new BusinessException("Invoice create date can not be before rent return date");
	}
	
	private boolean checkIfExistCustomer(int userId) {
		var result = this.customerService.getByUserId(userId);
		if(result == null) {
			throw new BusinessException("Can not find customer in this id.");
		}
		return true;
	}
	
	private boolean checkIfExistRentalCar(int rentalCarId) {
		if(!this.rentalCarService.checkIfExistById(rentalCarId)) {
			throw new BusinessException("Can not find rental car in this id.");
		}
		return true;
	}
	
	private boolean checkIfExistInvoiceId(int invoiceId) {
		var result = this.invoiceDao.getByInvoiceId(invoiceId);
		if(result == null) {
			throw new BusinessException("Can not find invoice in this id.");
		}
		return true;
	}
	
}
