package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CityService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.ListRentalCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.DeleteRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.RentalCarDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private ModelMapperService modelMapperService;	
	private CustomerService customerService;
	private CityService cityService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, @Lazy CarMaintenanceService carMaintenanceService,
			@Lazy CarService carService, ModelMapperService modelMapperService, AdditionalServiceService additionalServiceService,
			CustomerService customerService, CityService cityService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.rentalCarDao = rentalCarDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.cityService = cityService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public DataResult<RentalCar> add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
		
		checkIfCarExistByCarId(createRentalCarRequest.getCarId()); 
		checkIfCarMaintenanceNotExistByCarId(createRentalCarRequest.getCarId());
		checkIfCarNotInRent(createRentalCarRequest.getCarId());
		checkIfCityIdCorrect(createRentalCarRequest.getRentCityId());
		checkIfCityIdCorrect(createRentalCarRequest.getReturnCityId());
		checkIfPlannedReturnDateAfterRentDate(createRentalCarRequest.getRentDate(), createRentalCarRequest.getPlannedReturnDate());
		
		RentalCar rentalCar = toAdd(createRentalCarRequest);	
		
		this.rentalCarDao.save(rentalCar);
		return new SuccessDataResult<RentalCar>(rentalCar, BusinessMessage.RENTALCARSERVICE_ADD);
	}

	@Override
	public DataResult<RentalCar> update(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
		
		checkIfExistById(updateRentalCarRequest.getRentalCarId());
		checkIfCityIdCorrect(updateRentalCarRequest.getReturnCityId());
		checkIfReturnDateAfterOrPresentPlannedReturnDate(this.rentalCarDao.getByRentalCarId(updateRentalCarRequest.getRentalCarId()).getPlannedReturnDate(), updateRentalCarRequest.getReturnDate());
		
		RentalCar rentalCar = toUpdate(updateRentalCarRequest);
		
		rentalCarDao.save(rentalCar);
		return new SuccessDataResult<RentalCar>(rentalCar, BusinessMessage.RENTALCARSERVICE_UPDATE);
	}

	@Override
	public Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException {
		
		checkIfExistById(deleteRentalCarRequest.getRentalCarId());
		
		this.rentalCarDao.deleteById(deleteRentalCarRequest.getRentalCarId());
		return new SuccessResult(BusinessMessage.RENTALCARSERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListRentalCarDto>> getAll() {
		
		List<RentalCar> result = this.rentalCarDao.findAll();
		List<ListRentalCarDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, ListRentalCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentalCarDto>>(response);
	}
	
	@Override
	public DataResult<List<GetRentalCarByCarIdDto>> getRentalCarByCarId(int carId) throws BusinessException {
		
		checkIfCarExistByCarId(carId);
		
		List<RentalCar> result = this.rentalCarDao.getByCar_carId(carId);
		List<GetRentalCarByCarIdDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, GetRentalCarByCarIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetRentalCarByCarIdDto>>(response);
	}

	@Override
	public DataResult<GetRentalCarByIdDto> getRentalCarById(int rentalCarId) throws BusinessException {
		
		checkIfExistById(rentalCarId);
		
		RentalCar result = this.rentalCarDao.getByRentalCarId(rentalCarId);
		GetRentalCarByIdDto response = this.modelMapperService.forDto().map(result, GetRentalCarByIdDto.class);
		
		return new SuccessDataResult<GetRentalCarByIdDto>(response);
	}

	public boolean checkIfExistById(int rentalCarId) throws BusinessException{
		if(this.rentalCarDao.getByRentalCarId(rentalCarId) == null) {
			throw new BusinessException(BusinessMessage.RENTALCARSERVICE_CHECKIFEXISTBYID_ERROR);
		}
		return true;
	}
	
	public boolean checkIfCarNotInRent(int carId) throws BusinessException {
		if(this.rentalCarDao.getByReturnDateAndCar_carId(null, carId) != null) {
			throw new BusinessException(BusinessMessage.RENTALCARSERVICE_CHECKIFCARNOTINRENT_ERROR);
		}
		return true;
	}
	
	
	private double totalPriceCalculate(RentalCar rentalCar) {
		
		long daysBetween = 0;
		double rentalCarTotalPrice = rentalCar.getTotalPrice();
		double totalPrice = 0;
		
		//araç fiyat hesaplama
		if(rentalCar.getReturnDate() == null) {
			daysBetween = ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getPlannedReturnDate());
			rentalCarTotalPrice = 0;//eğer ilk kiralama ise rentalCarTotalPrice null olacağından dolayı 0 değerini atadım.
						
		}else {
			daysBetween = ChronoUnit.DAYS.between(rentalCar.getPlannedReturnDate(), rentalCar.getReturnDate());
		}
		
		totalPrice = (double) daysBetween*this.carService.getById(rentalCar.getCar().getCarId()).getData().getDailyPrice();
		
		//additional service fiyat hesaplama
		if(checkIfOrderedAdditionalService(rentalCar.getRentalCarId())) {
			
			for(OrderedAdditionalService orderedAdditionalService : rentalCar.getOrderedAdditionalServices()) {
				totalPrice += (double) daysBetween*orderedAdditionalService.getAdditionalService().getAdditionalServiceDailyPrice();
				
			}
		}
		
		//farklı şehir fiyat farkı hesaplama
		if(!rentalCar.getRentCity().equals(rentalCar.getReturnCity())) {
			totalPrice += 750;
			
		}
		
		//eğer planlanan tarihten sonra araç teslim edilirse eski fiyatın üzerine ekleme
		totalPrice = totalPrice + rentalCarTotalPrice;
		
		return totalPrice;
	}

	private boolean checkIfPlannedReturnDateAfterRentDate(LocalDate rentDate, LocalDate plannedReturnDate) throws BusinessException {
		if(!rentDate.isBefore(plannedReturnDate)) {
			throw new BusinessException(BusinessMessage.RENTALCARSERVICE_CHECKIFPLANNEDRETURNDATEAFTERRENTDATE_ERROR);
		}
		return true;
	}
	
	private boolean checkIfReturnDateAfterOrPresentPlannedReturnDate(LocalDate plannedReturnDate, LocalDate returnDate) {
		if(returnDate.isBefore(plannedReturnDate)) {
			throw new BusinessException(BusinessMessage.RENTALCARSERVICE_CHECKIFRETURNDATEAFTERORPRESENTPLANNEDRETURNDATE_ERROR);
		}
		return true;
	}
	
	private RentalCar toAdd(CreateRentalCarRequest createRentalCarRequest) {
		
		RentalCar rentalCar = new RentalCar();//this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
		rentalCar.setRentalCarId(0);
		rentalCar.setRentDate(createRentalCarRequest.getRentDate());
		rentalCar.setReturnDate(null);
		rentalCar.setCar(this.carService.getByIdForOtherServices(createRentalCarRequest.getCarId()));;
		rentalCar.setCustomer(this.customerService.getByUserId(createRentalCarRequest.getUserId()));
		rentalCar.setRentCity(this.cityService.getByIdForOtherService(createRentalCarRequest.getRentCityId()));
		rentalCar.setReturnCity(this.cityService.getByIdForOtherService(createRentalCarRequest.getReturnCityId()));
		rentalCar.setRentOdometer(getCarOdometer(rentalCar.getCar().getCarId()));
		rentalCar.setReturnOdometer(0);
		rentalCar.setPlannedReturnDate(createRentalCarRequest.getPlannedReturnDate());
		rentalCar.setTotalPrice(totalPriceCalculate(rentalCar));
		
		return rentalCar;
	}

	private RentalCar toUpdate (UpdateRentalCarRequest updateRentalCarRequest) {
		
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(updateRentalCarRequest.getRentalCarId());
		
	
		
		rentalCar.setReturnCity(this.cityService.getByIdForOtherService(updateRentalCarRequest.getReturnCityId()));
		rentalCar.setReturnDate(updateRentalCarRequest.getReturnDate());
		rentalCar.setTotalPrice(totalPriceCalculate(rentalCar));
		rentalCar.setReturnOdometer(updateRentalCarRequest.getReturnOdometer());
		setCarOdometer(rentalCar);
		
		return rentalCar;
	}
	
	private boolean checkIfCityIdCorrect(int cityId) {
		if(cityId > 0 && cityId < 82) {
			return true;
		}
		throw new BusinessException(BusinessMessage.RENTALCARSERVICE_CHECKIFCITYIDCORRECT_ERROR);
	}
	
	public void totalPriceCalculateAfterAddAdditionalService(int rentalCarId) {
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(rentalCarId);
		rentalCar.setTotalPrice(totalPriceCalculate(rentalCar));
		this.rentalCarDao.save(rentalCar);
	}
	
	private boolean checkIfOrderedAdditionalService(int rentalCarId) {
		if(this.orderedAdditionalServiceService.checkIfRentalCarId(rentalCarId)) {
			return true;
		}
		return false;
	}
	
	private long getCarOdometer(int carId) {
		return this.carService.getById(carId).getData().getOdometer();
	}
	
	private void setCarOdometer(RentalCar rentalCar) {
		if(rentalCar.getRentOdometer() > rentalCar.getReturnOdometer()) {
			throw new BusinessException(BusinessMessage.RENTALCARSERVICE_SETCARODOMETER_ERROR);
		}
		this.carService.toSetCarOdometer(rentalCar.getCar().getCarId(), rentalCar.getReturnOdometer());
	}
	
	private boolean checkIfCarExistByCarId(int carId) {
		this.carService.checkIfExistByCarId(carId);
		return true;
	}
	
	private boolean checkIfCarMaintenanceNotExistByCarId(int carId) {
		this.carMaintenanceService.checkIfCarNotInMaintenance(carId);
		return true;
	}
	
	public RentalCar getByRentalCarIdForOtherServices(int rentalCarId) {
		return this.rentalCarDao.getByRentalCarId(rentalCarId);
	}
}

	