package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.UserDao;
@Service
public class UserManager implements UserService {

	UserDao userDao;
	
	@Autowired
	public UserManager(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean checkIfNotExistByEmail(String email) {
		
		var result = this.userDao.getByEmail(email);
		
		if(result != null) {
			throw new BusinessException("E-mail already exist.");
		}
		return true;
	}
}
