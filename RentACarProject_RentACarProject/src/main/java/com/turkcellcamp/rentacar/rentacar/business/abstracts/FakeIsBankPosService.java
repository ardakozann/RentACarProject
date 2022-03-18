package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.bankDtos.ListBankDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.bankRequests.CreateBankRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface FakeIsBankPosService {
	Result add(CreateBankRequest createBankRequest);
	DataResult <List<ListBankDto>> getAll();
	DataResult <List<ListBankDto>> getById(int bankId);
}
