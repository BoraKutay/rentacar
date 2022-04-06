package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.corporateCustomerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import com.turkcell.rentacar.entities.concretes.Customer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	
	
	
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {
		
		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
		List<CorporateCustomerListDto> response = result.stream()
				.map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer, CorporateCustomerListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,BusinessMessages.CORPORATE_CUSTOMERS + BusinessMessages.LIST);
	}

	@Override
	public DataResult<CorporateCustomerByIdDto> getById(int id) throws BusinessException {
    	
		checkIfCorporateCustomerIsExists(id);
    	
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);	
        CorporateCustomerByIdDto response = this.modelMapperService.forDto().map(corporateCustomer, CorporateCustomerByIdDto.class);

        return new SuccessDataResult<CorporateCustomerByIdDto>(response,BusinessMessages.CORPORATE_CUSTOMER + BusinessMessages.GET_BY_ID + id);
	}

	@Override
	public Result add(@Valid @Email @RequestBody CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER + BusinessMessages.ADD);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerIsExists(deleteCorporateCustomerRequest.getCorporateCustomerId());
		
		this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.getCorporateCustomerId());

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER + BusinessMessages.DELETE);
	}

	@Override
	public Result update(@RequestBody @Valid @Email UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerIsExists(updateCorporateCustomerRequest.getCorporateCustomerId());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER + BusinessMessages.UPDATE);
	}
	
	private boolean checkIfCorporateCustomerIsExists(int id) throws BusinessException{
		
		if(corporateCustomerDao.existsById(id) == false) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER + BusinessMessages.DOES_NOT_EXISTS  + id);
		}
		return true;
	}

	@Override
	public Customer getCustomerById(int id) {
		return null;
		
	}

	@Override
	public boolean checkIfCustomerExists(int id) throws BusinessException {
		
		if(corporateCustomerDao.existsById(id) == false) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER + BusinessMessages.DOES_NOT_EXISTS  + id);
		}
		return true;
	}

	@Override
	public boolean checkIfCorparateCustomerExists(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

}
