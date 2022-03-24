package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.creditCardDtos.CreditCardByIdDto;
import com.turkcell.rentacar.business.dtos.creditCardDtos.CreditCardListDto;
import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.DeleteCreditCardRequest;
import com.turkcell.rentacar.business.requests.UpdateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CreditCardService {
	
	DataResult<List<CreditCardListDto>> getAll();
	Result add(CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException;
	DataResult<CreditCardByIdDto> getById(int id) throws BusinessException;
	DataResult<List<CreditCardListDto>> getByCustomerId(int id) throws BusinessException;
	Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException;
	Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException;
}
