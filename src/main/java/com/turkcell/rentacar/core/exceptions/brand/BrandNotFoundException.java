package com.turkcell.rentacar.core.exceptions.brand;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class BrandNotFoundException extends BusinessException {

	public BrandNotFoundException(String message) {
		super(message);
	}

}
