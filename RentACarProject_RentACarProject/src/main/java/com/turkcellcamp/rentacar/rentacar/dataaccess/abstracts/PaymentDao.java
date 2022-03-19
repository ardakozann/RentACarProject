package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Payment;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface PaymentDao extends JpaRepository<Payment, Integer> {
	public Payment getByPaymentId(int paymentId);
}
