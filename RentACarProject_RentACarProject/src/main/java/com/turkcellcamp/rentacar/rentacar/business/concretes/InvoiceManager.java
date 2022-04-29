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
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.GetInvoiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.invoiceDtos.ListInvoiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.InvoiceDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Customer;
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
	public DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest) {
		
		checkIfNotExistInvoiceByInvoiceNo(createInvoiceRequest.getInvoiceNo());

		checkIfExistCustomer(createInvoiceRequest.getUserId());
		checkIfExistRentalCar(createInvoiceRequest.getRentalCar().getRentalCarId());
		checkIfInvoiceExistForRentalCar(createInvoiceRequest.getRentalCar().getRentalCarId());
		if(checkIfRentalCar_ReturnDateEqualPlannedReturnDate(createInvoiceRequest.getRentalCar().getRentalCarId())){
			return new ErrorDataResult<Invoice>(null, BusinessMessage.INVOICESERVICE_ADD_ERROR);
		}

		Invoice invoice = toSetForAddMethod(createInvoiceRequest);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessDataResult<Invoice>(invoice, BusinessMessage.INVOICESERVICE_ADD);
	}
	
	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		//BURADA BİR İŞLEM YAPILMAYACAK, SİLİNECEK.
		checkIfExistByInvoiceId(updateInvoiceRequest.getInvoiceId());
		
		Invoice invoice = this.invoiceDao.getByInvoiceId(updateInvoiceRequest.getInvoiceId());
		invoice.setCreateDate(updateInvoiceRequest.getCreateDate());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(BusinessMessage.INVOICESERVICE_UPDATE);
	}

	@Override
	public Result delete(int id) {
		
		checkIfExistByInvoiceId(id);
		
		this.invoiceDao.deleteById(id);
		
		return new SuccessResult(BusinessMessage.INVOICESERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		List<Invoice> result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto()
				.map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		response = toSetReturnDateForGetAllMethod(result, response);
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<GetInvoiceByIdDto> getByInvoiceId(int invoiceId) {
		
		checkIfExistByInvoiceId(invoiceId);
		
		Invoice result = this.invoiceDao.getByInvoiceId(invoiceId);
		GetInvoiceByIdDto response = this.modelMapperService.forDto().map(result, GetInvoiceByIdDto.class);
		
		return new SuccessDataResult<GetInvoiceByIdDto>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDateOfBetween(LocalDate startDate, LocalDate finishDate) {
		
		List<Invoice> result = this.invoiceDao.findByCreateDateBetween(startDate, finishDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().
				map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getInvoiceByCustomer(int id) {

		checkIfExistCustomer(id);
		
		List<Invoice> result = this.invoiceDao.getByCustomer_UserId(id);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().
				map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	
	private Invoice toSetForAddMethod(CreateInvoiceRequest createInvoiceRequest) {

		Customer customer = this.customerService.getByUserId(createInvoiceRequest.getUserId());
		
		Invoice invoice = new Invoice();
		
		invoice.setCustomer(customer);
		invoice.setInvoiceNo(createInvoiceRequest.getInvoiceNo());
		invoice.setRentalCar(createInvoiceRequest.getRentalCar());
		
		if(invoice.getRentalCar().getReturnDate() == null) {
			invoice.setCreateDate(createInvoiceRequest.getRentalCar().getRentDate());
			invoice.setRentDate(createInvoiceRequest.getRentalCar().getRentDate());
			invoice.setReturnDate(createInvoiceRequest.getRentalCar().getPlannedReturnDate());
			invoice.setNumberDays(ChronoUnit.DAYS.between(createInvoiceRequest.getRentalCar().getRentDate(), createInvoiceRequest.getRentalCar().getPlannedReturnDate()));
			invoice.setRentTotalPrice(createInvoiceRequest.getRentalCar().getTotalPrice());
		
			
		}
	
			
		else {
			double secondInvoicePrice = createInvoiceRequest.getRentalCar().getTotalPrice() - this.invoiceDao.getByRentalCar_RentalCarId
					(createInvoiceRequest.getRentalCar().getRentalCarId()).get(0).getRentTotalPrice();
			
			invoice.setCreateDate(createInvoiceRequest.getRentalCar().getReturnDate());
			invoice.setRentDate(createInvoiceRequest.getRentalCar().getPlannedReturnDate());
			invoice.setReturnDate(createInvoiceRequest.getRentalCar().getReturnDate());
			invoice.setNumberDays(ChronoUnit.DAYS.between(createInvoiceRequest.getRentalCar().getPlannedReturnDate(), createInvoiceRequest.getRentalCar().getReturnDate()));
			invoice.setRentTotalPrice(secondInvoicePrice);
		}
		return invoice;
	}	
	
	private List<ListInvoiceDto> toSetReturnDateForGetAllMethod(List<Invoice> result, List<ListInvoiceDto> response){
		for(int i = 0; i < response.size() ; i++) {
			response.get(i).setRenturnDate(result.get(i).getReturnDate());
			response.get(i).setRentalCarId(result.get(i).getRentalCar().getRentalCarId());		}
		return response;
	}
	
	private boolean checkIfNotExistInvoiceByInvoiceNo(long invoiceNo) {
		Invoice result = this.invoiceDao.getByInvoiceNo(invoiceNo);
		if(result != null) {
			throw new BusinessException(BusinessMessage.INVOICESERVICE_CHECKIFNOTEXISTBYINVOICENO_ERROR);
		}
		return true;
	}

	
	private boolean checkIfExistCustomer(int userId) {
		this.customerService.checkIfExistById(userId);
		return true;
	}
	
	private boolean checkIfExistRentalCar(int rentalCarId) {
		this.rentalCarService.checkIfExistById(rentalCarId);
		return true;
	}
	
	private boolean checkIfExistByInvoiceId(int invoiceId) {
		Invoice result = this.invoiceDao.getByInvoiceId(invoiceId);
		if(result == null) {
			throw new BusinessException(BusinessMessage.INVOICESERVICE_CHECKIFEXISTBYINVOICEID_ERROR);
		}
		return true;
	}
	
	private boolean checkIfInvoiceExistForRentalCar(int rentalCarId) {
		List<Invoice> result = this.invoiceDao.getByRentalCar_RentalCarId(rentalCarId);
		if(result != null) {	
			if(result.size() == 1 && this.rentalCarService.getRentalCarById(rentalCarId).getData().getReturnDate() == null ||
					result.size() == 2 ) {
				throw new BusinessException(BusinessMessage.INVOICESERVICE_CHECKIFEXISTFORRENTALCAR_ERROR);
				}
			}
		return true;
		}
	

	private boolean checkIfRentalCar_ReturnDateEqualPlannedReturnDate(int rentalCarId) {
		if(this.rentalCarService.getByRentalCarIdForOtherServices(rentalCarId).getReturnDate() != null &&
					this.rentalCarService.getByRentalCarIdForOtherServices(rentalCarId).getPlannedReturnDate().
					isEqual(this.rentalCarService.getByRentalCarIdForOtherServices(rentalCarId).getReturnDate())) {
			return false;
		}
		return true;
	}
	

}