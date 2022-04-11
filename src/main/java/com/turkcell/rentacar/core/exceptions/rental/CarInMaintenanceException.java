package com.turkcell.rentacar.core.exceptions.rental;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarInMaintenanceException extends BusinessException {

	public CarInMaintenanceException(String message) {
		super(message);
	}

}
