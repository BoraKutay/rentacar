package com.turkcell.rentacar.core.exceptions.color;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class ColorNameNotUniqueException extends BusinessException {

	public ColorNameNotUniqueException(String message) {
		super(message);
	}

}
