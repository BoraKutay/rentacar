package com.turkcell.rentacar.core.exceptions.orderedAdditionalService;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class OrderedAdditionalServiceNotFoundException extends BusinessException {

	public OrderedAdditionalServiceNotFoundException(String message) {
		super(message);
	}

}
