package com.turkcell.rentacar.core.exceptions.customer;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends BusinessException {

	public CustomerNotFoundException(String message) {
		super(message);
	}

}
