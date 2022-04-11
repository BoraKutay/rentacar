package com.turkcell.rentacar.core.exceptions.corporateCustomer;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CorporateCustomerNotFoundException extends BusinessException {

	public CorporateCustomerNotFoundException(String message) {
		super(message);
	}

}
