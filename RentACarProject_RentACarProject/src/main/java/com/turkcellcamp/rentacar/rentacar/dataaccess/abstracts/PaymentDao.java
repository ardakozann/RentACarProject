package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Payment;

@Repository
public interface PaymentDao {
	public Payment getByPaymentId(int paymentId);
}
