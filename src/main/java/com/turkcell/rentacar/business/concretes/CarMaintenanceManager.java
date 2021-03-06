package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.carMaintenance.CarMaintenanceNotFoundException;
import com.turkcell.rentacar.core.exceptions.carMaintenance.CarNotAvailableException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.Rental;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private CarService carService;
	
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, @Lazy RentalService rentalService, @Lazy CarService carService ) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.carService = carService;
	}
	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		
		List<CarMaintenance> result = carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCES + BusinessMessages.LISTED);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarIsAvailable(createCarMaintenanceRequest.getCarCarId());
		checkIfCarIsInMaintenance(createCarMaintenanceRequest.getCarCarId());
		
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.CAR_MAINTENANCE + BusinessMessages.ADDED);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceExists(updateCarMaintenanceRequest.getCarMaintenanceId());
		checkIfCarIsAvailable(updateCarMaintenanceRequest.getCarCarId());
		checkIfCarIsInMaintenance(updateCarMaintenanceRequest.getCarCarId());
		
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.CAR_MAINTENANCE + BusinessMessages.UPDATED);
	}

	@Override
	public DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException {
		
		checkIfCarMaintenanceExists(carMaintenanceId);
		
        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);
        CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceByIdDto.class);

        return new SuccessDataResult<CarMaintenanceByIdDto>(response, BusinessMessages.CAR_MAINTENANCE + BusinessMessages.GET_BY_ID + carMaintenanceId);
	}

	@Override
	public Result deleteById(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceExists(deleteCarMaintenanceRequest.getCarMaintenanceId());
		
        this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getCarMaintenanceId());
        
        return new SuccessResult(BusinessMessages.CAR_MAINTENANCE + BusinessMessages.DELETED);
	}
	
	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException {
		
		carService.checkIfCarExists(carId);
		
		List<CarMaintenance> result = this.carMaintenanceDao.getByCarCarId(carId);
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCES + BusinessMessages.LISTED);
	}
	
	private void checkIfCarIsAvailable(int carId) throws BusinessException{
		
		DataResult<List<RentalListDto>> result = this.rentalService.getAllByCarCarId(carId);
		
		List<Rental> response = result.getData().stream()
				.map(rental->this.modelMapperService.forDto().map(rental, Rental.class))
				.collect(Collectors.toList());
		
		for (Rental rental : response) {
			
			if(rental.getEndDate() == null || rental.getEndDate().isAfter(LocalDate.now())) {
				
				throw new CarNotAvailableException(BusinessMessages.CAR_IS_NOT_AVAILABLE + rental.getEndDate());
				
			}
		}
					
	}
	
	
	private void checkIfCarIsInMaintenance(int carId) throws BusinessException{
		
		DataResult<List<CarMaintenanceListDto>> result = getByCarId(carId);
		List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());
		
		for(CarMaintenance carMaintenance : response) {
            
			if(carMaintenance.getReturnDate() == null || LocalDate.now().isBefore(carMaintenance.getReturnDate())) {
				throw new CarNotAvailableException(BusinessMessages.CAR_IS_IN_MAINTENANCE);
			}
		}
	}
	
	
    private boolean checkIfCarMaintenanceExists(int id) throws BusinessException {
    	
    	if(carMaintenanceDao.existsById(id) == false) {
    		throw new CarMaintenanceNotFoundException(BusinessMessages.CAR_MAINTENANCE + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
    }
    


}
