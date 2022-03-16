package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.carDamageDtos.CarDamageByIdDto;
import com.turkcell.rentacar.business.dtos.carDamageDtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarDamageService {
	
	DataResult<List<CarDamageListDto>> getAll();
	
	Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException;
	
	DataResult<CarDamageByIdDto> getById(int id) throws BusinessException;
	
	Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException;
	
	Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException;
}
