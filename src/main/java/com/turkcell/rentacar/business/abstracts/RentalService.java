package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.rentalDtos.RentalDtoById;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalListDto;
import com.turkcell.rentacar.business.requests.FinishRentalRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentalRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rental;

public interface RentalService {

	DataResult<List<RentalListDto>> getAll();
	
	DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException;
	
	DataResult<Rental> addForIndividualCustomer(CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer) throws BusinessException;
	
	DataResult<Rental> addForCorporateCustomer(CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer) throws BusinessException;
	
	DataResult<RentalDtoById> getById(int id) throws BusinessException;
	
	Rental getRentalById(int id) throws BusinessException;
	
	Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException;
	
	Result delete(DeleteRentalRequest deleteRentalRequest) throws BusinessException;
	
	Result finishRentalForIndividualCustomer(FinishRentalRequest finishRentalRequest) throws BusinessException;

	Result finishRentalForCorporateCustomer(FinishRentalRequest finishRentalRequest) throws BusinessException;
	
	boolean checkIfRentalExists(int id) throws BusinessException;
}
