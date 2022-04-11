package com.turkcell.rentacar.core.exceptions.color;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class ColorNotFoundException extends BusinessException{

	public ColorNotFoundException(String message) {
		super(message);
	}

}
