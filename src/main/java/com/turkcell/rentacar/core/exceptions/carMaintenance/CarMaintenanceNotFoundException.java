package com.turkcell.rentacar.core.exceptions.carMaintenance;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarMaintenanceNotFoundException extends BusinessException {

	public CarMaintenanceNotFoundException(String message) {
		super(message);
	}

}
