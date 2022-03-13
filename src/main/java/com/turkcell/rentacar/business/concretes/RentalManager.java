package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalDtoById;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentalRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	
	
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService,@Lazy CarService carService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public DataResult<List<RentalListDto>> getAll() {
		List<Rental> result = this.rentalDao.findAll();
		List<RentalListDto> response = result.stream().map(rental->this.modelMapperService.forDto().map(rental,RentalListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<RentalListDto>>(response,"Rents listed");
	}
    


	@Override
	public Result add(CreateRentalRequest createRentalRequest) throws BusinessException {
		
		checkIfCarIsAvailable(createRentalRequest.getCarCarId(),createRentalRequest.getStartDate());
		checkIfStartDateBeforeThanEndDate(createRentalRequest.getStartDate(),createRentalRequest.getEndDate());
		
		Rental rental = this.modelMapperService.forDto().map(createRentalRequest, Rental.class);
		rental.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getAllByRentalId(rental.getRentalId()));	
		rental.setAdditionalPrice(calculateAdditionalPriceForReturnLocation(rental));
		
		this.rentalDao.save(rental);
		
		return new SuccessResult("Rent is added");
	}

	@Override
	public DataResult<RentalDtoById> getById(int id) throws BusinessException {
		
		checkIfRentalExists(id);
		
		Rental rental = this.rentalDao.getById(id);
		RentalDtoById rentalDtoById =this.modelMapperService.forDto().map(rental, RentalDtoById.class);
		
		return new SuccessDataResult<RentalDtoById>(rentalDtoById,"Rent listed");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException {
		
		checkIfRentalExists(updateRentalRequest.getRentalId());
		checkIfCarIsAvailable(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
		checkIfStartDateBeforeThanEndDate(updateRentalRequest.getStartDate(),updateRentalRequest.getEndDate());
		
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setAdditionalPrice(calculateAdditionalPriceForReturnLocation(rental));
		
		this.rentalDao.save(rental);
		
		return new SuccessResult("Rent updated");
	}
	
	private void checkIfCarIsAvailable(int carId ,LocalDate startDate) throws BusinessException {
		checkIfCarIsRented(carId,startDate);
		checkIfCarIsInMaintenance(carId, startDate);
	}
	
    private void checkIfCarIsRented(int carId, LocalDate startDate) throws BusinessException {
    	
    	this.carService.checkIfCarExists(carId);
    	DataResult<List<RentalListDto>> result=getAllByCarCarId(carId);
    	List<Rental> response = result.getData().stream()
    			.map(rental -> this.modelMapperService.forDto()
    			.map(rental, Rental.class))
    			.collect(Collectors.toList());
    	
    	
    	for (Rental rental:response) {
    		if(!rental.getEndDate().isBefore(startDate)) {
    			throw new BusinessException("Car is already rented!");
    		}
    	}
    }
    
    private void checkIfCarIsInMaintenance(int carId, LocalDate startDate) throws BusinessException {
    	
		DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);
		List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());
		
		for(CarMaintenance carMaintenance : response) {
			                                                                     
			if(carMaintenance.getReturnDate() == null || startDate.isBefore(carMaintenance.getReturnDate())) {
				throw new BusinessException("Car is in maintenance!");
			}
		}
    }
	

	@Override
	public DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException {
		
		this.carService.checkIfCarExists(id);
		
		List<Rental> result = this.rentalDao.getAllByCarCarId(id);
		List<RentalListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentalListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentalListDto>>(response,"Car's rent info listed");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) throws BusinessException {
		
		checkIfRentalExists(deleteRentalRequest.getRentalId());
		
        this.rentalDao.deleteById(deleteRentalRequest.getRentalId());
        
        return new SuccessResult("Rental is deleted.");
	}
	
    private boolean checkIfRentalExists(int id) throws BusinessException {
    	if(rentalDao.existsById(id) == false) {
    		throw new BusinessException("Rental does not exists by id:" + id);
    	}
		return true;
    }
    
    private boolean checkIfRentalIsReturnDifferentCity(Rental rental) {
		
    	if(rental.getCityOfPickUpLocation() == rental.getCityOfReturnLocation()) {
    		return false;
    	}
    	
    	return true;
    	
    }
    
    private double calculateAdditionalPriceForReturnLocation(Rental rental) {
    	
    	double additionalPrice = 0;
    	
    	if(checkIfRentalIsReturnDifferentCity(rental)) {
    		additionalPrice = 750;
    	}
    	
		return additionalPrice;
    	
    }
    
    
    private void checkIfStartDateBeforeThanEndDate(LocalDate startDate,LocalDate endDate) throws BusinessException {
        if(endDate.isBefore(startDate)){
            throw new BusinessException("End date should be after the start date! ");
        }
      }
    


	
	

}
