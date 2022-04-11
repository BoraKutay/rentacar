package com.turkcell.rentacar.core.exceptions.creditCard;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CreditCardNotFoundException extends BusinessException {

	public CreditCardNotFoundException(String message) {
		super(message);
	}

}
