package com.turkcell.rentacar.core.exceptions.rental;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class EndDateBeforeThanStartDateException extends BusinessException {

	public EndDateBeforeThanStartDateException(String message) {
		super(message);
	}

}
