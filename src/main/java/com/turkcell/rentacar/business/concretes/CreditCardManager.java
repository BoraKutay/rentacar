package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.creditCardDtos.CreditCardByIdDto;
import com.turkcell.rentacar.business.dtos.creditCardDtos.CreditCardListDto;
import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.DeleteCreditCardRequest;
import com.turkcell.rentacar.business.requests.UpdateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentacar.entities.concretes.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	
	
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService, CustomerService customerService) {
		
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<List<CreditCardListDto>> getAll() {
		
		List<CreditCard> result = this.creditCardDao.findAll();
		List<CreditCardListDto> response = result.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
		.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CreditCardListDto>>(response,BusinessMessages.CREDIT_CARDS + BusinessMessages.LIST);
	}
	
	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException {
		
		CreditCard creditCard = this.modelMapperService.forDto().map(createCreditCardRequest, CreditCard.class);
		creditCard.setCustomer(this.customerService.getCustomerById(customerId));
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(BusinessMessages.CREDIT_CARD + BusinessMessages.ADD);
	}

	@Override
	public DataResult<CreditCardByIdDto> getById(int id) throws BusinessException {
		
		checkIfCreditCardExists(id);
		
		CreditCard result = this.creditCardDao.getById(id);
		
		CreditCardByIdDto response = this.modelMapperService.forDto().map(result, CreditCardByIdDto.class);
		
		return new SuccessDataResult<CreditCardByIdDto>(response, BusinessMessages.CREDIT_CARD + BusinessMessages.GET_BY_ID + id);
				

	}

	@Override
	public DataResult<List<CreditCardListDto>> getByCustomerId(int id) throws BusinessException {
		
		this.customerService.checkIfCustomerExists(id);
		
		List<CreditCard> result = this.creditCardDao.getAllByCustomer_CustomerId(id);
		
		List<CreditCardListDto> response = result.stream()
				.map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CreditCardListDto>>(response, BusinessMessages.CREDIT_CARDS + BusinessMessages.LIST);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
		
		checkIfCreditCardExists(updateCreditCardRequest.getCreditCardId());
		
		CreditCard creditCard = this.modelMapperService.forDto().map(updateCreditCardRequest, CreditCard.class);
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(BusinessMessages.CREDIT_CARD + BusinessMessages.UPDATE);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException {
		
		checkIfCreditCardExists(deleteCreditCardRequest.getCreditCardId());
		
		this.creditCardDao.deleteById(deleteCreditCardRequest.getCreditCardId());
		
		return new SuccessResult(BusinessMessages.CREDIT_CARD + BusinessMessages.DELETE);
	}
	
	
	private boolean checkIfCreditCardExists(int id) throws BusinessException {
    	
		if(creditCardDao.existsById(id) == false) {
    		throw new BusinessException(BusinessMessages.CREDIT_CARD + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
	}




}
