package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CustomerDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;
	
	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@Override
	public Customer getByUserId(int id) {
		
		checkIfExistById(id);
		
		return this.customerDao.getByUserId(id);
	}
	
	public boolean checkIfExistById(int id) {
		Customer result = this.customerDao.getByUserId(id);
		if(result == null) {
			throw new BusinessException(BusinessMessage.CUSTOMERSERVICE_CHECKIFEXISTBYID);
		}
		return true;
	}
}