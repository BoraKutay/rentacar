package com.turkcell.rentacar.core.exceptions.additionalService;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class AdditionalServiceNotUniqueException extends BusinessException{

	public AdditionalServiceNotUniqueException(String message) {
		super(message);
	}

}
