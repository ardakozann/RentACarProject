package com.turkcellcamp.rentacar.rentacar.business.dtos.bankDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListBankDto {
	
	private int bankId;
	
	private int bankNo;
	
	private String bankName;
}
