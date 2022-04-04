package com.turkcellcamp.rentacar.rentacar.business.constants.messages;

public class BusinessMessage {

	
	public static final String ADDITIONALSERVICE_ADD = "AdditionalService.Added";
	public static final String ADDITIONALSERVICE_UPDATE = "AdditionalService.Added";
	public static final String ADDITIONALSERVICE_DELETE = "AdditionalService.Deleted";
	public static final String ADDITIONALSERVICE_GETBYID = "Success";
	public static final String ADDITIONALSERVICE_CHECKIFEXISTBYID_ERROR = "Can not find Additional Service you wrote id.";
	public static final String ADDITIONALSERVICE_CHECKIFEXISTBYNAME_ERROR = "The additional service is exist.";

	public static final String BRANDSERVICE_ADD = "Brand.Added";
	public static final String BRANDSERVICE_UPDATE = "Brand.Updated";
	public static final String BRANDSERVICE_DELETE = "Brand.Deleted";
	public static final String BRANDSERVICE_GETBYID = "Success";
	public static final String BRANDSERVICE_CHECKIFEXISTBYNAME_ERROR = "The brand is exist.";
	public static final String BRANDSERVICE_CHECKIFEXISTBYID_ERROR = "Can not brand with this id.";
	
	public static final String CARACCIDENTSERVICE_ADD = "CarAccident.Added";
	public static final String CARACCIDENTSERVICE_UPDATE = "CarAccident.Updated";
	public static final String CARACCIDENTSERVICE_DELETE = "CarAccident.Deleted";
	public static final String CARACCIDENTSERVICE_CHECKIFEXISTBYCARID_ERROR = "Can not find car with this car id.";
	public static final String CARACCIDENTSERVICE_CHECKIFEXISTBYID_ERROR = "Can not find car accident with this car accident id.";
	
	public static final String CARDSERVICE_ADD = "Card.Added";
	public static final String CARDSERVICE_UPDATE = "Card.Updated";
	public static final String CARDSERVICE_DELETE = "Card.Deleted";
	public static final String CARDSERVICE_CHECKIFEXISTCARD_ERROR = "The card added before.";
	public static final String CARDSERVICE_CHECKIFEXISTBYID_ERROR = "Can not find card with this id.";

	public static final String CARMAINTENANCESERVICE_ADD = "CarMaintenance.Added";
	public static final String CARMAINTENANCESERVICE_UPDATE = "CarMaintenance.Updated";
	public static final String CARMAINTENANCESERVICE_DELETE = "CarMaintenance.Deleted";
	public static final String CARMAINTENANCESERVICE_CHECKIFEXISTBYID_ERROR = "The car maintenance id you wrote is not exist.";
	public static final String CARMAINTENANCESERVICE_CHECKIFCARNOTINMAINTENANCE_ERROR = "The car in maintenance.";
	public static final String CARMAINTENANCESERVICE_CHECKIFCARINMAINTENANCE_ERROR = "The car is not in maintenance.";
	
	public static final String CARSERVICE_ADD = "Car.Added";
	public static final String CARSERVICE_UPDATE = "Car.Updated";
	public static final String CARSERVICE_DELETE = "Car.Deleted";
	public static final String CARSERVICE_GETCARBYDAILYPRICE_ERROR = "Can not find a car which is daily price you wrote is below";
	public static final String CARSERVICE_CHECKIFEXISTBYID = "The car you wrote id is not exist.";
	
	public static final String COLORSERVICE_ADD = "Color.Added";
	public static final String COLORSERVICE_UPDATE = "Color.Updated";
	public static final String COLORSERVICE_DELETE = "Color.Deleted";
	public static final String COLORSERVICE_CHECKIFNOTEXISTBYCOLORNAME_ERROR = "The Color you wrote is exist.";
	public static final String COLORSERVICE_CHECKIFEXISTBYCOLORID_ERROR = "Can not find color with this id.";

	public static final String CORPORATECUSTOMERSERVICE_ADD = "CorporateCustomer.Added";
	public static final String CORPORATECUSTOMERSERVICE_UPDATE = "CorporateCustomer.Updated";
	public static final String CORPORATECUSTOMERSERVICE_DELETE = "CorporateCustomer.Deleted";
	public static final String CORPORATECUSTOMERSERVICE_CHECKIFEXISTBYTAXNUMBER_ERROR = "Can not find Corporate Customer with this tax number.";
	public static final String CORPORATECUSTOMERSERVICE_CHECKIFEXISTBYEMAIL_ERROR = "Can not find Corporate Customer with this e-mail.";
	
	public static final String CUSTOMERSERVICE_CHECKIFEXISTBYID = "Can not find Customer with this id.";
	
	public static final String INDIVIDUALCUSTOMERSERVICE_ADD = "IndividualCustomer.Added";
	public static final String INDIVIDUALCUSTOMERSERVICE_UPDATE = "IndividualCustomer.Updated";
	public static final String INDIVIDUALCUSTOMERSERVICE_DELETE = "IndividualCustomer.Deleted";
	public static final String INDIVIDUALCUSTOMERSERVICE_CHECKIFNOTEXISTBYIDENTITYNUMBER_ERROR = "Tax Number already exist.";
	public static final String INDIVIDUALCUSTOMERSERVICE_CHECKIFEXISTBYEMAIL_ERROR = "Can not find Corporate Customer with this id.";
	
	public static final String INVOICESERVICE_ADD = "Invoice.Added";
	public static final String INVOICESERVICE_ADD_ERROR = "There is no need to create invoice";
	public static final String INVOICESERVICE_UPDATE = "Invoice.Updated";
	public static final String INVOICESERVICE_DELETE = "Invoice.Deleted";
	public static final String INVOICESERVICE_CHECKIFNOTEXISTBYINVOICENO_ERROR = "This invoice no already exist.";
	public static final String INVOICESERVICE_CHECKIFEXISTBYINVOICEID_ERROR = "Can not find invoice in this id.";
	public static final String INVOICESERVICE_CHECKIFEXISTFORRENTALCAR_ERROR = "Invioce already exist for this rent.";
	
	public static final String ORDEREDADDITIONALSERVICESERVICE_ADD = "OrderedAdditionalService.Added";
	public static final String ORDEREDADDITIONALSERVICESERVICE_UPDATE = "OrderedAdditionalService.Updated";
	public static final String ORDEREDADDITIONALSERVICESERVICE_DELETE = "OrderedAdditionalService.Deleted";
	public static final String ORDEREDADDITIONALSERVICESERVICE_CHECKIFEXISTADDITIONALSERVICEINRENT_ERROR = "The additional service has been added to that car before.";
	public static final String ORDEREDADDITIONALSERVICESERVICE_CHECKIFNOTEXISTADDITIONALSERVICEINRENT_ERROR = "The additional service can not find on that car.";
	public static final String ORDEREDADDITIONALSERVICESERVICE_CHECKIFEXISTBYID_ERROR = "Can not find ordered additional service id you wrote.";
	
	public static final String PAYMENTSERVICE_ADD = "Payment.Success";
	public static final String PAYMENTSERVICE_ADD_ERROR = "There is no need payment because of return date and planned return date.";
	
	public static final String RENTALCARSERVICE_ADD = "RentalCar.Added";
	public static final String RENTALCARSERVICE_UPDATE = "RentalCar.Updated";
	public static final String RENTALCARSERVICE_DELETE = "RentalCar.Deleted";
	public static final String RENTALCARSERVICE_CHECKIFEXISTBYID_ERROR = "Can not find rental car you wrote id";
	public static final String RENTALCARSERVICE_CHECKIFCARNOTINRENT_ERROR = "The car in rent.";
	public static final String RENTALCARSERVICE_CHECKIFPLANNEDRETURNDATEAFTERRENTDATE_ERROR = "Can not be return date before rent date.";
	public static final String RENTALCARSERVICE_CHECKIFRETURNDATEAFTERORPRESENTPLANNEDRETURNDATE_ERROR = "Can not be return date before planned return date.";
	public static final String RENTALCARSERVICE_CHECKIFCITYIDCORRECT_ERROR = "The id you wrote is not city code.";
	public static final String RENTALCARSERVICE_SETCARODOMETER_ERROR = "Rent odometer can not be under return odometer.";
	
	public static final String USERSERVICE_CHECKIFNOTEXISTBYEMAIL_ERROR = "E-mail already exist.";
}
