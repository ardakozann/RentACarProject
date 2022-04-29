package com.turkcellcamp.rentacar.rentacar.business.abstracts;

public interface UserService {
	public boolean checkIfNotExistByEmail(String email);
	public boolean checkIfExistByEmail(String email);
}