package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.individualCustomerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	
	
	
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = result.stream()
				.map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, IndividualCustomerListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<IndividualCustomerListDto>>(response,BusinessMessages.INDIVIDUAL_CUSTOMERS + BusinessMessages.LIST);
	}

	@Override
	public DataResult<IndividualCustomerByIdDto> getById(int id) throws BusinessException {
    	
		checkIfIndividualCustomerIsExists(id);
    	
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);	
        IndividualCustomerByIdDto response = this.modelMapperService.forDto().map(individualCustomer, IndividualCustomerByIdDto.class);

        return new SuccessDataResult<IndividualCustomerByIdDto>(response,BusinessMessages.INDIVIDUAL_CUSTOMER + BusinessMessages.GET_BY_ID + id);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER + BusinessMessages.ADD);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
		
		checkIfIndividualCustomerIsExists(deleteIndividualCustomerRequest.getIndividualCustomerId());
		
		this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getIndividualCustomerId());

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER + BusinessMessages.DELETE);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		
		checkIfIndividualCustomerIsExists(updateIndividualCustomerRequest.getIndividualCustomerId());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER + BusinessMessages.UPDATE);
	}
	
	private boolean checkIfIndividualCustomerIsExists(int id) throws BusinessException{
		
		if(individualCustomerDao.existsById(id) == false) {
			throw new BusinessException(BusinessMessages.INDIVIDUAL_CUSTOMER + BusinessMessages.DOES_NOT_EXISTS + id);
		}
		return true;
	}

	@Override
	public IndividualCustomer getCustomerById(int id) {
		return this.individualCustomerDao.getById(id);
	}

}
