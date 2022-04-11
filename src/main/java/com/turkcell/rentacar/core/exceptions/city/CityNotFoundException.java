package com.turkcell.rentacar.core.exceptions.city;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CityNotFoundException extends BusinessException {

	public CityNotFoundException(String message) {
		super(message);
	}

}
