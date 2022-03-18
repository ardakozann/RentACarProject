package com.turkcellcamp.rentacar.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="banks")
@Entity
public class Bank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bank_id")
	private int bankId;
	
	@Column(name="bank_no")
	private int bankNo;
	
	@Column(name="bank_name")
	private String bankName;
	
	@OneToMany(mappedBy="creditCard")
	private List<CreditCard> creditCards;
}
