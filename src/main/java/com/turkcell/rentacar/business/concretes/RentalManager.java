package com.turkcell.rentacar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalDtoById;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalListDto;
import com.turkcell.rentacar.business.requests.FinishRentalRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentalRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentacar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	
	
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private AdditionalServiceService additionalServiceService;
	private CustomerService customerService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private CityService cityService;
	
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService,@Lazy CarService carService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService, 
			@Lazy AdditionalServiceService additionalServiceService, @Lazy CustomerService customerService, 
			@Lazy IndividualCustomerService individualCustomerService,CityService cityService,
			@Lazy CorporateCustomerService corporateCustomerService) {
		
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.cityService = cityService;
	}

	@Override
	public DataResult<List<RentalListDto>> getAll() {
		
		List<Rental> result = this.rentalDao.findAll();
		List<RentalListDto> response = result.stream().map(rental->this.modelMapperService.forDto().map(rental,RentalListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentalListDto>>(response, BusinessMessages.RENTALS + BusinessMessages.LIST);
	}
	
	@Override
	public DataResult<Rental> addForIndividualCustomer(CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer) throws BusinessException {
	
		
		checkIfCarIsAvailable(createRentalRequestForIndividualCustomer.getCar_CarId(),createRentalRequestForIndividualCustomer.getStartDate());
		checkIfStartDateBeforeThanEndDate(createRentalRequestForIndividualCustomer.getStartDate(),createRentalRequestForIndividualCustomer.getEndDate());
		checkIfIndividualCustomerExists(createRentalRequestForIndividualCustomer.getIndividualCustomerId());
		checkIfCityExists(createRentalRequestForIndividualCustomer.getPickUpLocationIdCityId());
		checkIfCityExists(createRentalRequestForIndividualCustomer.getReturnLocationIdCityId());
		
		Rental rental = this.modelMapperService.forDto().map(createRentalRequestForIndividualCustomer, Rental.class);
		
		rental.setRentalId(0);
		
		this.rentalDao.saveAndFlush(rental);
		
		rental.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getAllByRentalId(rental.getRentalId()));	
		this.orderedAdditionalServiceService.orderAdditionalServices(createRentalRequestForIndividualCustomer.getAdditionalServicesId() ,rental.getRentalId());
		rental.setStartKilometer(this.carService.getById(createRentalRequestForIndividualCustomer.getCar_CarId()).getData().getCarKilometer());
		rental.setTotalPrice(calculateTotalPriceOfRental(rental,createRentalRequestForIndividualCustomer.getAdditionalServicesId(),createRentalRequestForIndividualCustomer.getPickUpLocationIdCityId(),createRentalRequestForIndividualCustomer.getReturnLocationIdCityId()));
		rental.setCustomer(this.individualCustomerService.getCustomerById(createRentalRequestForIndividualCustomer.getIndividualCustomerId()));
		
		Rental rentalEntity = this.rentalDao.save(rental);
		
		return new SuccessDataResult<Rental>(rentalEntity, BusinessMessages.RENTAL + BusinessMessages.ADD);
	
		
		
	}
	
	@Override
	public DataResult<Rental> addForCorporateCustomer(CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer) throws BusinessException {
		
		checkIfCarIsAvailable(createRentalRequestForCorporateCustomer.getCar_CarId(),createRentalRequestForCorporateCustomer.getStartDate());
		checkIfStartDateBeforeThanEndDate(createRentalRequestForCorporateCustomer.getStartDate(),createRentalRequestForCorporateCustomer.getEndDate());
		checkIfCorporateCustomerExists(createRentalRequestForCorporateCustomer.getCorporateCustomerId());
		checkIfCityExists(createRentalRequestForCorporateCustomer.getPickUpLocationIdCityId());
		checkIfCityExists(createRentalRequestForCorporateCustomer.getReturnLocationIdCityId());
		
		Rental rental = this.modelMapperService.forDto().map(createRentalRequestForCorporateCustomer, Rental.class);
		
		rental.setRentalId(0);
		
		this.rentalDao.saveAndFlush(rental);
		
		rental.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getAllByRentalId(rental.getRentalId()));	
		this.orderedAdditionalServiceService.orderAdditionalServices(createRentalRequestForCorporateCustomer.getAdditionalServicesId() ,rental.getRentalId());
		rental.setStartKilometer(this.carService.getById(createRentalRequestForCorporateCustomer.getCar_CarId()).getData().getCarKilometer());
		rental.setTotalPrice(calculateTotalPriceOfRental(rental,createRentalRequestForCorporateCustomer.getAdditionalServicesId(),createRentalRequestForCorporateCustomer.getPickUpLocationIdCityId(),createRentalRequestForCorporateCustomer.getReturnLocationIdCityId()));
		rental.setCustomer(this.customerService.getCustomerById(createRentalRequestForCorporateCustomer.getCorporateCustomerId()));
		
		Rental rentalEntity = this.rentalDao.save(rental);
		
		return new SuccessDataResult<Rental>(rentalEntity,BusinessMessages.RENTAL + BusinessMessages.ADD);
	
		
		
	}

	
	@Override
	public Result finishRentalForIndividualCustomer(FinishRentalRequest finishRentalRequest) throws BusinessException {
		
		checkIfRentalExists(finishRentalRequest.getRentalId());
		
		
		Rental rental = this.rentalDao.getById(finishRentalRequest.getRentalId());
		
		checkRentalReturnDateExpiredForIndiviual(rental);
		
		rental.setEndKilometer(finishRentalRequest.getEndKilometer());
		
		this.carService.updateCarKilometer(rental.getCar().getCarId(), finishRentalRequest.getEndKilometer());
		
		this.rentalDao.save(rental);
		
		return new SuccessResult(BusinessMessages.RENT_IS_OVER);
		
		
	}
	
	@Override
	public Result finishRentalForCorporateCustomer(FinishRentalRequest finishRentalRequest) throws BusinessException {
		
		checkIfRentalExists(finishRentalRequest.getRentalId());
		
		
		Rental rental = this.rentalDao.getById(finishRentalRequest.getRentalId());
		
		checkRentalReturnDateExpiredForCorporate(rental);
		
		rental.setEndKilometer(finishRentalRequest.getEndKilometer());
		
		this.carService.updateCarKilometer(rental.getCar().getCarId(), finishRentalRequest.getEndKilometer());
		
		this.rentalDao.save(rental);
		
		return new SuccessResult(BusinessMessages.RENT_IS_OVER);
		
		
	}
		

	@Override
	public DataResult<RentalDtoById> getById(int id) throws BusinessException {
		
		checkIfRentalExists(id);
		
		Rental rental = this.rentalDao.getById(id);
		RentalDtoById rentalDtoById =this.modelMapperService.forDto().map(rental, RentalDtoById.class);
		
		return new SuccessDataResult<RentalDtoById>(rentalDtoById,BusinessMessages.RENTAL + BusinessMessages.GET_BY_ID + id);
	}
	
	@Override
	public Rental getRentalById(int id) throws BusinessException{
		
		checkIfRentalExists(id);
		
		Rental rental = this.rentalDao.getById(id);
		
		return rental;
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException {
		
		checkIfRentalExists(updateRentalRequest.getRentalId());
		checkIfCarIsAvailable(updateRentalRequest.getCarCarId(),updateRentalRequest.getStartDate());
		checkIfStartDateBeforeThanEndDate(updateRentalRequest.getStartDate(),updateRentalRequest.getEndDate());
		
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		this.rentalDao.saveAndFlush(rental);
		
		this.orderedAdditionalServiceService.orderAdditionalServices(updateRentalRequest.getAdditionalServicesId() ,rental.getRentalId());
		
		rental.setTotalPrice(calculateTotalPriceOfRental(rental,updateRentalRequest.getAdditionalServicesId(),updateRentalRequest.getCityOfPickUpLocationId(),updateRentalRequest.getCityOfReturnLocationId())); 
		
		this.rentalDao.save(rental);
		
		return new SuccessResult(BusinessMessages.RENTAL + BusinessMessages.UPDATE);
	}

	@Override
	public DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException {
		
		this.carService.checkIfCarExists(id);
		
		List<Rental> result = this.rentalDao.getAllByCarCarId(id);
		List<RentalListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentalListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentalListDto>>(response,BusinessMessages.RENTALS + BusinessMessages.LIST + BusinessMessages.BY_CAR + id);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) throws BusinessException {
		
		checkIfRentalExists(deleteRentalRequest.getRentalId());
		
        this.rentalDao.deleteById(deleteRentalRequest.getRentalId());
        
        return new SuccessResult(BusinessMessages.RENTAL + BusinessMessages.DELETE);
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
				throw new BusinessException(BusinessMessages.CAR_IS_IN_MAINTENANCE);
			}
		}
    }
	

	
    private boolean checkIfRentalExists(int id) throws BusinessException {
    	
    	if(rentalDao.existsById(id) == false) {
    		
    		throw new BusinessException(BusinessMessages.RENTAL + BusinessMessages.DOES_NOT_EXISTS + id);
    		
    	}
		return true;
    }
    
    private boolean checkIfRentalIsReturnDifferentCity(int pickUpLocationId, int returnLocationId) {
		

    	if( pickUpLocationId == returnLocationId ) {
    		
    		return false;
    		
    	}
    	
    	return true;
    	
    }
    
    private double calculateAdditionalPriceForReturnLocation(int pickUpLocationId, int returnLocationId) {
    	
    	double additionalPrice = 0;
    	
    	if(checkIfRentalIsReturnDifferentCity(pickUpLocationId, returnLocationId)) {
    		
    		additionalPrice = 750;
    		
    	}
    	
		return additionalPrice;
    	
    }
    
    
    private void checkIfStartDateBeforeThanEndDate(LocalDate startDate,LocalDate endDate) throws BusinessException {
    	
        if(endDate.isBefore(startDate)){
        	
            throw new BusinessException(BusinessMessages.END_DATE_CANNOT_BEFORE_START_DATE);
            
        }
      }
    
    private boolean checkIfCityExists(int cityId) throws BusinessException {
    	
    	this.cityService.checkIfCityExists(cityId);
    	
		return true;
    }
    
    private boolean checkIfIndividualCustomerExists(int customerId) throws BusinessException {
    	
    	this.individualCustomerService.checkIfCustomerExists(customerId);
    	
    	return true;
    	
    }
    
    private boolean checkIfCorporateCustomerExists(int customerId) throws BusinessException {
    	
    	this.corporateCustomerService.checkIfCustomerExists(customerId);
    	
    	return true;
    	
    }
    
    private double calculateTotalPriceOfRental(Rental rental, List<Integer> orderedAdditionalServicesId, int pickUpLocationId, int returnLocationId) throws BusinessException {
    	
    	double totalPrice = 0;
    	
    	int daysBetween = (int)ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
    	
    	daysBetween = manageDaysBetweenIfZero(daysBetween);
    	
    	double additionalServicePrice = this.additionalServiceService.calculateAdditionalPriceOfServices(orderedAdditionalServicesId);

    	double carDailyPrice = carService.getById(rental.getCar().getCarId()).getData().getDailyPrice();
    	
    	totalPrice = (carDailyPrice + additionalServicePrice) * daysBetween + calculateAdditionalPriceForReturnLocation(pickUpLocationId,returnLocationId); 
    	
    	return totalPrice;
    	
    }
    
    private int manageDaysBetweenIfZero(int daysBetween) {
    	
    	if (daysBetween == 0) {
    		return 1;
    	}
    	
    	return daysBetween;
    }
    
    
	private List<Integer> setIdOfAdditionalServices(List<OrderedAdditionalService> orderedAdditionalServices){
		
		List<Integer> additionalServicesIds = new ArrayList<Integer>();
		
		for (int i = 0; i < orderedAdditionalServices.size(); i++) {
			
			additionalServicesIds.add(orderedAdditionalServices.get(i).getAdditionalService().getAdditionalServiceId());
			
		}
		
		return additionalServicesIds;
		
	}

	private void checkRentalReturnDateExpiredForIndiviual(Rental rental) throws BusinessException {
		if(LocalDate.now().isAfter(rental.getEndDate())) {

			
			addForIndividualCustomer(setCreateRentalRequestForIndividualCustomer(rental));
					
		}
		
	}
	
	
	private void checkRentalReturnDateExpiredForCorporate(Rental rental) throws BusinessException {
		if(LocalDate.now().isAfter(rental.getEndDate())) {

			addForCorporateCustomer(setCreateRentalRequestForCorporateCustomerFields(rental));
					
		}
		
	}
	
	private CreateRentalRequestForCorporateCustomer setCreateRentalRequestForCorporateCustomerFields(Rental rental) {
		
		CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer = new CreateRentalRequestForCorporateCustomer();
		
		createRentalRequestForCorporateCustomer.setEndDate(LocalDate.now());
		createRentalRequestForCorporateCustomer.setStartDate(rental.getEndDate());
		createRentalRequestForCorporateCustomer.setCar_CarId(rental.getCar().getCarId());
		createRentalRequestForCorporateCustomer.setCorporateCustomerId(rental.getCustomer().getCustomerId());
		createRentalRequestForCorporateCustomer.setPickUpLocationIdCityId(rental.getReturnLocation().getCityId());
		createRentalRequestForCorporateCustomer.setReturnLocationIdCityId(rental.getReturnLocation().getCityId());
		createRentalRequestForCorporateCustomer.setAdditionalServicesId(setIdOfAdditionalServices(rental.getOrderedAdditionalServices()));
		
		return createRentalRequestForCorporateCustomer;
	}
	
	private CreateRentalRequestForIndividualCustomer setCreateRentalRequestForIndividualCustomer(Rental rental) {
		
		
		CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer = new CreateRentalRequestForIndividualCustomer();
		
		createRentalRequestForIndividualCustomer.setEndDate(LocalDate.now());
		createRentalRequestForIndividualCustomer.setStartDate(rental.getEndDate());
		createRentalRequestForIndividualCustomer.setCar_CarId(rental.getCar().getCarId());
		createRentalRequestForIndividualCustomer.setIndividualCustomerId(rental.getCustomer().getCustomerId());
		createRentalRequestForIndividualCustomer.setPickUpLocationIdCityId(rental.getReturnLocation().getCityId());
		createRentalRequestForIndividualCustomer.setReturnLocationIdCityId(rental.getReturnLocation().getCityId());
		createRentalRequestForIndividualCustomer.setAdditionalServicesId(setIdOfAdditionalServices(rental.getOrderedAdditionalServices()));
		
		return createRentalRequestForIndividualCustomer;
		
	}

    


	
	

}
