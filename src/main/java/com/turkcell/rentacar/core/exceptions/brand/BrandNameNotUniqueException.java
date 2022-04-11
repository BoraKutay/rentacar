package com.turkcell.rentacar.core.exceptions.brand;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class BrandNameNotUniqueException extends BusinessException {

	public BrandNameNotUniqueException(String message) {
		super(message);
	}

}
