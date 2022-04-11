package com.turkcell.rentacar.core.exceptions.carDamage;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarDamageNotFoundException extends BusinessException {

	public CarDamageNotFoundException(String message) {
		super(message);
	}

}
