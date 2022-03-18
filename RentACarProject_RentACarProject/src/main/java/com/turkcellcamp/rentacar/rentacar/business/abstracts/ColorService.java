package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos.GetColorByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos.ListColorDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.CreateColorRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.DeleteColorRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.UpdateColorRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface ColorService {
	Result add(CreateColorRequest createColorRequest) throws BusinessException;
	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
	Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException;
	DataResult <List<ListColorDto>> getAll();
	DataResult <GetColorByIdDto> getById(int colorId) throws BusinessException;
	boolean checkIfExistByColorId(int colorId) throws BusinessException;
}
