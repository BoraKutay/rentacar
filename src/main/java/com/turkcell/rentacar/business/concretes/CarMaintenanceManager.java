package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.RentalListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
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
	
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, RentalService rentalService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
	}
	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		List<CarMaintenance> result = carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "Maintenance are listed successfuly.");
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		checkIfCarIsAvailable(createCarMaintenanceRequest);
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Maintenance is added.");
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		checkIfCarMaintenanceExist(updateCarMaintenanceRequest.getCarMaintenanceId());
		checkIfCarIsAvailableForUpdate(updateCarMaintenanceRequest);
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setCarMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Maintenance is updated.");
	}

	@Override
	public DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException {
		checkIfCarMaintenanceExist(carMaintenanceId);
        CarMaintenance carMaintenance = this.carMaintenanceDao.getById(carMaintenanceId);

        CarMaintenanceByIdDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceByIdDto.class);

        return new SuccessDataResult<CarMaintenanceByIdDto>(response);
	}

	@Override
	public Result deleteById(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		checkIfCarMaintenanceExist(deleteCarMaintenanceRequest.getCarMaintenanceId());
        this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getCarMaintenanceId());
        return new SuccessResult("Maintenance is deleted.");
	}
	
	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) throws BusinessException {
		checkIfCarMaintenanceExist(carId);
		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_CarId(carId);
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, "Car maintenances listed successfully.");
	}
	
	private void checkIfCarIsAvailable(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException{
		DataResult<List<RentalListDto>> result = this.rentalService.getAllByCarCarId(createCarMaintenanceRequest.getCarCarId());
		
		List<Rental> response = result.getData().stream()
				.map(rental->this.modelMapperService.forDto().map(rental, Rental.class))
				.collect(Collectors.toList());
		
		for (Rental rental : response) {
			
			if(rental.getEndDate() == null || rental.getEndDate().isAfter(LocalDate.now())) {
				
				throw new BusinessException("Car is not available until " + rental.getEndDate());
				
			}
		}
		
				
	}
	
	private void checkIfCarIsAvailableForUpdate(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException{
		
		DataResult<List<RentalListDto>> result = this.rentalService.getAllByCarCarId(updateCarMaintenanceRequest.getCarCarId());
		
		List<Rental> response = result.getData().stream()
				.map(rental->this.modelMapperService.forDto().map(rental, Rental.class))
				.collect(Collectors.toList());
		
		for (Rental rental : response) {
			
			if(rental.getEndDate() == null || rental.getEndDate().isAfter(LocalDate.now())) {
				
				throw new BusinessException("Car is not available until " + rental.getEndDate());
				
			}
		}
		
				
	}
	
    private boolean checkIfCarMaintenanceExist(int id) throws BusinessException {
    	if(carMaintenanceDao.existsById(id) == false) {
    		throw new BusinessException("Car maintenance does not exist by id:" + id);
    	}
		return true;
    }


}
