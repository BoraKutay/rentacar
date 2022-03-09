package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.RentalDtoById;
import com.turkcell.rentacar.business.dtos.RentalListDto;
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
		
		checkIfCarAvailable(createRentalRequest);
		Rental rental = this.modelMapperService.forDto().map(createRentalRequest, Rental.class);
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
		checkIfCarAvailableForUpdate(updateRentalRequest);
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		this.rentalDao.save(rental);
		return new SuccessResult("Rent updated");
	}
	
	private void checkIfCarAvailable(CreateRentalRequest createRentalRequest) throws BusinessException {
		DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(createRentalRequest.getCarCarId());
		List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());
		
		for(CarMaintenance carMaintenance : response) {
			                                                                     //8 11                          //11 Mart
			if(carMaintenance.getReturnDate() == null || createRentalRequest.getStartDate().isBefore(carMaintenance.getReturnDate())) {
				throw new BusinessException("Car is not available!");
			}
		}
	}
	
	private void checkIfCarAvailableForUpdate(UpdateRentalRequest updateRentalRequest) throws BusinessException {
		checkIfRentalExists(updateRentalRequest.getRentalId());
		DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(updateRentalRequest.getCarCarId());
		List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());

		
		for(CarMaintenance carMaintenance : response) {
			if(carMaintenance.getReturnDate() == null || updateRentalRequest.getStartDate().isBefore(carMaintenance.getReturnDate())) {
				throw new BusinessException("Car is not available!");
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
    		throw new BusinessException("Rental does not exist by id:" + id);
    	}
		return true;
    }
	
	

}
