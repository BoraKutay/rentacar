package com.turkcell.rentacar.core.exceptions.additionalService;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class AdditionalServiceNotFoundException extends BusinessException {

	public AdditionalServiceNotFoundException(String message) {
		super(message);
	}

}
