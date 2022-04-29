package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.ColorService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos.GetColorByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.colorDtos.ListColorDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.CreateColorRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.DeleteColorRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests.UpdateColorRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	private ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/getall")
	public DataResult <List<ListColorDto>> getAll() {
		return this.colorService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {
		return this.colorService.add(createColorRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException{
		return this.colorService.update(updateColorRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteColorRequest deleteColorRequest) throws BusinessException{
		return this.colorService.delete(deleteColorRequest);
	}
	
	@GetMapping("/getbyid")
	public DataResult <GetColorByIdDto> getById(@RequestParam @Valid int colorId) throws BusinessException {
		return this.colorService.getById(colorId);
	}
}

