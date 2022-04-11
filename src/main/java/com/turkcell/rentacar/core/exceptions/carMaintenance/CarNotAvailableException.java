package com.turkcell.rentacar.core.exceptions.carMaintenance;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarNotAvailableException extends BusinessException{

	public CarNotAvailableException(String message) {
		super(message);
	}

}
