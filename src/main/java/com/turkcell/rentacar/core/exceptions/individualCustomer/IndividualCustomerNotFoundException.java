package com.turkcell.rentacar.core.exceptions.individualCustomer;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class IndividualCustomerNotFoundException extends BusinessException {

	public IndividualCustomerNotFoundException(String message) {
		super(message);
	}

}
