package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CardService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.requests.cardRequests.CreateCardRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.invoiceRequests.CreateInvoiceRequestForTransactional;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequestForTransactional;
import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentAdditionalRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.ErrorResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.PaymentDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Card;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Invoice;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Payment;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.RentalCar;

@Service
public class PaymentManager implements PaymentService {

	private PosService posService;
	private ModelMapperService modelMapperService;
	private PaymentDao paymentDao;
	private RentalCarService rentalCarService;
	private InvoiceService invoiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private CustomerService customerService;
	private CardService cardService;
	
	@Autowired
	public PaymentManager(@Lazy PosService posService, ModelMapperService modelMapperService, PaymentDao paymentDao,
			RentalCarService rentalCarService, InvoiceService invoiceService, OrderedAdditionalServiceService orderedAdditionalServiceService,
			CustomerService customerService, CardService cardService) {
		this.posService = posService;
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
		this.rentalCarService = rentalCarService;
		this.invoiceService = invoiceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.customerService = customerService;
		this.cardService = cardService;
	}

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public Result add(CreatePaymentRequest createPaymentRequest) {
	
		RentalCar rentalCar = rentalCarAddMethod(createPaymentRequest.getCreateRentalCarRequest());
		
		orderedAdditionalServiceAddMethod(rentalCar.getRentalCarId(), createPaymentRequest.getCreateOrderedAdditionalServiceRequestForTransactional());
		
		Invoice invoice = invoiceAddMethod(rentalCar, createPaymentRequest.getCreateInvoiceRequestForTransactional());
		
		cardAddMethod(createPaymentRequest.isSaveCard(), invoice.getCustomer().getUserId(), createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber(),
				createPaymentRequest.getCardCvvNumber());
		
		toSendPosService(createPaymentRequest.getCardOwnerName(), createPaymentRequest.getCardNumber(), createPaymentRequest.getCardCvvNumber());
	
		Payment payment = toSetPaymentMethod(rentalCar, invoice);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(BusinessMessage.PAYMENTSERVICE_ADD);
		
	}
	
	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public Result addForAdditionalTime(CreatePaymentAdditionalRequest createPaymentAdditionalRequest) {
		
		RentalCar rentalCar = rentalCarUpdateMethod(createPaymentAdditionalRequest.getUpdateRentalCarRequest());
		
		orderedAdditionalServiceAddMethod(rentalCar.getRentalCarId(), createPaymentAdditionalRequest.getCreateOrderedAdditionalServiceRequestForTransactional());
	
		Invoice invoice = invoiceAddMethod(rentalCar, createPaymentAdditionalRequest.getCreateInvoiceRequestForTransactional());
		
		if(invoice == null) {
			return new ErrorResult(BusinessMessage.PAYMENTSERVICE_ADD_ERROR);
		}
		
		cardAddMethod(createPaymentAdditionalRequest.isSaveCard(), invoice.getCustomer().getUserId(), createPaymentAdditionalRequest.getCardOwnerName(), createPaymentAdditionalRequest.getCardNumber(),
				createPaymentAdditionalRequest.getCardCvvNumber());
		
		toSendPosService(createPaymentAdditionalRequest.getCardOwnerName(), createPaymentAdditionalRequest.getCardNumber(), createPaymentAdditionalRequest.getCardCvvNumber());
	
		Payment payment = toSetPaymentMethod(rentalCar, invoice);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(BusinessMessage.PAYMENTSERVICE_ADD);
		
	}
	
	private void toSendPosService(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		this.posService.payments(cardOwnerName, cardNumber, cardCvvNumber);
	}
	
	private RentalCar rentalCarAddMethod(CreateRentalCarRequest createRentalCarRequest) {
		return this.rentalCarService.add(createRentalCarRequest).getData();
	}
	
	private RentalCar rentalCarUpdateMethod(UpdateRentalCarRequest updateRentalCarRequest) {
		return this.rentalCarService.update(updateRentalCarRequest).getData();
	}
	
	private Invoice invoiceAddMethod(RentalCar rentalCar, CreateInvoiceRequestForTransactional createInvoiceRequestForTransactional) {
		CreateInvoiceRequest createInvoiceRequest = this.modelMapperService.forRequest().map(createInvoiceRequestForTransactional, CreateInvoiceRequest.class);
		createInvoiceRequest.setRentalCar(rentalCar);
		return this.invoiceService.add(createInvoiceRequest).getData();
	}
	
	private void orderedAdditionalServiceAddMethod( int rentalCarId, CreateOrderedAdditionalServiceRequestForTransactional createOrderedAdditionalServiceRequestForTransactional) {
		
		CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = new CreateOrderedAdditionalServiceRequest();
		createOrderedAdditionalServiceRequest.setRentalCarId(rentalCarId);
		
		for(int i = 0 ; i < createOrderedAdditionalServiceRequestForTransactional.getAdditionalServiceIds().size() ; i++) {
			createOrderedAdditionalServiceRequest.setAdditionalServiceId(createOrderedAdditionalServiceRequestForTransactional.getAdditionalServiceIds().get(i));
			this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
		}
	}
	
	private void cardAddMethod(boolean saveCard, int userId, String cardOwnerName, String cardNumber, int cardCvvNumber) {
		if(saveCard) {		
			CreateCardRequest createCardRequest = new CreateCardRequest();
			createCardRequest.setCardOwnerName(cardOwnerName);
			createCardRequest.setCardNumber(cardNumber);
			createCardRequest.setCardCvvNumber(cardCvvNumber);
			createCardRequest.setUserId(userId);
			this.cardService.add(createCardRequest);
			}
	}
	
	private Payment toSetPaymentMethod(RentalCar rentalCar, Invoice invoice) {
		Payment payment = new Payment();
		payment.setInvoice(invoice);
		payment.setRentalCar(rentalCar);
		
		return payment;
	}

}