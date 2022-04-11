package com.turkcell.rentacar.core.exceptions.car;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarNotFoundException extends BusinessException {

	public CarNotFoundException(String message) {
		super(message);
	}

}
