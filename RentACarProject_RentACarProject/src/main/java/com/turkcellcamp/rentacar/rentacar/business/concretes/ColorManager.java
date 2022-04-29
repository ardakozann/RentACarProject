package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.ColorService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos.GetColorByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos.ListColorDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.CreateColorRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.DeleteColorRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.UpdateColorRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.ColorDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {
	
	private ColorDao colorDao;
	private ModelMapperService modelMapperService;		
	
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		
		checkIfNotExistByColorName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		this.colorDao.save(color);
		
		return new SuccessResult(BusinessMessage.COLORSERVICE_ADD);	
	}
	
	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		
		checkIfExistByColorId(updateColorRequest.getColorId());
		checkIfNotExistByColorName(updateColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		
		this.colorDao.save(color);
		
		return new SuccessResult(BusinessMessage.COLORSERVICE_UPDATE);
	}
	
	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		
		checkIfExistByColorId(deleteColorRequest.getColorId());
		
		this.colorDao.deleteById(deleteColorRequest.getColorId());
		
		return new SuccessResult(BusinessMessage.COLORSERVICE_DELETE);	
	}
	
	@Override
	public DataResult <GetColorByIdDto> getById(int colorId) throws BusinessException {
		
		checkIfExistByColorId(colorId);
		
		GetColorByIdDto response = this.modelMapperService.forDto().map(this.colorDao.getByColorId(colorId), GetColorByIdDto.class);
		
		return new SuccessDataResult<GetColorByIdDto>(response);
	}
	
	@Override
	public DataResult <List<ListColorDto>> getAll() {
		
		List<Color> result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream().map(color->this.modelMapperService.forDto()
				.map(color, ListColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response);
	}
	
	
	private boolean checkIfNotExistByColorName(String colorName) throws BusinessException {
		
		Color color = this.colorDao.getByColorName(colorName);
		if(color != null) {
			throw new BusinessException(BusinessMessage.COLORSERVICE_CHECKIFNOTEXISTBYCOLORNAME_ERROR);
		}
		return true;
	}
	
	public boolean checkIfExistByColorId(int colorId) throws BusinessException {
		
		Color color = this.colorDao.getByColorId(colorId);
		if(color == null) {
			throw new BusinessException(BusinessMessage.COLORSERVICE_CHECKIFEXISTBYCOLORID_ERROR);
		}
		return true;
	}

}