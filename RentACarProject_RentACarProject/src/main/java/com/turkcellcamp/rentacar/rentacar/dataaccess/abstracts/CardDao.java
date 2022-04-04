package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Card;
@Repository
public interface CardDao extends JpaRepository<Card, Integer> {
	Card getByCardId(int cardId);
	List<Card> getByCustomer_UserId(int userId);
	Card getByCardOwnerNameAndCardNumberAndCardCvvNumber(String cardOwnderName, String cardNumber, int cardCvvNumber);
}