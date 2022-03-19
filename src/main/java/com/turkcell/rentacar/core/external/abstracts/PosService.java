package com.turkcell.rentacar.core.external.abstracts;


import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PosService {
	
	public Result isCardValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;
	public Result isPaymentSuccess(double totalPayment) throws BusinessException;
}
