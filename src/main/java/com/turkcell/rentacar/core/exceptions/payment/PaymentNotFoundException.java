package com.turkcell.rentacar.core.exceptions.payment;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class PaymentNotFoundException extends BusinessException{

	public PaymentNotFoundException(String message) {
		super(message);
	}

}
