package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.carDamageDtos.CarDamageByIdDto;
import com.turkcell.rentacar.business.dtos.carDamageDtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentacar.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	
	

	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService, CarService carService) {
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}
	
	@Override
	public DataResult<List<CarDamageListDto>> getAll() {
		
		List<CarDamage> result = this.carDamageDao.findAll();
		List<CarDamageListDto> response = result.stream().map(cardamage -> this.modelMapperService.forDto()
				.map(cardamage, CarDamageListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarDamageListDto>>(response,"Car damages listed.");
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
		
		this.carService.checkIfCarExists(createCarDamageRequest.getCarId());
		
		CarDamage result = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		
		this.carDamageDao.save(result);
		
		return new SuccessResult("Car damage saved.");
	}

	@Override
	public DataResult<CarDamageByIdDto> getById(int id) throws BusinessException {
		
		checkIfCarDamageExists(id);
		CarDamage result = this.carDamageDao.getById(id);
		CarDamageByIdDto response = this.modelMapperService.forDto().map(result, CarDamageByIdDto.class);
		
		return new SuccessDataResult<CarDamageByIdDto>(response,"Car damage listed.");
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
		
		checkIfCarDamageExists(updateCarDamageRequest.getCarDamageId());
		this.carService.checkIfCarExists(updateCarDamageRequest.getCarId());
		
		CarDamage result = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		
		this.carDamageDao.save(result);
		
		return new SuccessResult("Car damage updated.");
	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
		
		checkIfCarDamageExists(deleteCarDamageRequest.getCarDamageId());
		
		this.carDamageDao.deleteById(deleteCarDamageRequest.getCarDamageId());
		
		return new SuccessResult("Car Damage deleted.");
	}
	
    private boolean checkIfCarDamageExists(int id) throws BusinessException {
    	
    	if(carDamageDao.existsById(id) == false) {
    		throw new BusinessException("Car damage does not exists by id:" + id);
    	}
		return true;
    }

}
