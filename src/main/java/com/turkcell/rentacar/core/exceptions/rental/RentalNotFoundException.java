package com.turkcell.rentacar.core.exceptions.rental;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class RentalNotFoundException extends BusinessException {

	public RentalNotFoundException(String message) {
		super(message);
	}

}
