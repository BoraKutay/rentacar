package com.turkcell.rentacar.core.exceptions.city;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CityNameNotUniqueException extends BusinessException{

	public CityNameNotUniqueException(String message) {
		super(message);
	}

}
