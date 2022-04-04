package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.individualCustomerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerService extends CustomerService {
	DataResult<List<IndividualCustomerListDto>> getAll();
	DataResult<IndividualCustomerByIdDto> getById(int id) throws BusinessException;
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
	IndividualCustomer getCustomerById(int id);
	boolean checkIfIndividualCustomerIsExists(int id) throws BusinessException;
}
