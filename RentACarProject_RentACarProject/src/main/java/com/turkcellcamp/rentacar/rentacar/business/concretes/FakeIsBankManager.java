package com.turkcellcamp.rentacar.rentacar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.FakeIsBankPosService;

@Service
public class FakeIsBankManager implements FakeIsBankPosService {

	@Override
	public void fakeIsBankService(String fullName, String cardNo, int Cvv) {
		System.out.println("İş bank ile ödendi.");
	}
}
