package com.turkcell.rentacar.core.adapters.abstracts;

import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PosAdapterService {
	
	Result isCardValid(String cardHolder, String cardNumber, int cvv, int month, int year) throws BusinessException;
	Result isPaymentSuccess(double totalPayment) throws BusinessException;
}
