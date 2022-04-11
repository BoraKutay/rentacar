package com.turkcell.rentacar.core.exceptions.rental;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarAlreadyRentedException extends BusinessException {

	public CarAlreadyRentedException(String message) {
		super(message);
	}

}
