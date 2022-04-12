package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.dtos.carDamageDtos.CarDamageByIdDto;
import com.turkcell.rentacar.business.dtos.carDamageDtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/car-damages")
public class CarDamagesController {
	
	private CarDamageService carDamageService;
	
	
	public CarDamagesController(CarDamageService carDamageService) {
		this.carDamageService = carDamageService;
	}

	@GetMapping("/getAll")
	DataResult<List<CarDamageListDto>> getAll() {
		
		return this.carDamageService.getAll();
		
	}
	
	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
		
		return this.carDamageService.add(createCarDamageRequest);
		
		
	}
	@GetMapping("/getById")
	DataResult<CarDamageByIdDto> getById(int id) throws BusinessException {
		
		return this.carDamageService.getById(id);
		
	}
	
	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
		return this.carDamageService.update(updateCarDamageRequest);
	}
	
	@DeleteMapping("/deleteById")
	Result delete(@RequestBody @Valid DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
		return this.carDamageService.delete(deleteCarDamageRequest);
	}
}
