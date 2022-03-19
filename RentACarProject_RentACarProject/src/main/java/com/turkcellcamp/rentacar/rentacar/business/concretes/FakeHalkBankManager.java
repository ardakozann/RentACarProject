package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.FakeHalkBankPosService;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;

@Service
public class FakeHalkBankManager implements FakeHalkBankPosService {

	@Override
	public void fakeHalkBankService(int Cvv, String fullName, String cardNo) {
		
		if(fullName.equals("Arda")) {
			System.out.println("Halk bankası ile ödeme gerçekleşti.");

		}
		else {
		throw new BusinessException("hatalı ödeme ");
		}
	}

}
