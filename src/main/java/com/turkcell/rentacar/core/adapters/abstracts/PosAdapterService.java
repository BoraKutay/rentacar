package com.turkcell.rentacar.core.adapters.abstracts;

import com.turkcell.rentacar.business.requests.createRequests.CreateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PosAdapterService {
	
	Result isCardValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;
	Result makePayment(CreateCreditCardRequest createCreditCardRequest,double totalPayment) throws BusinessException;
}
