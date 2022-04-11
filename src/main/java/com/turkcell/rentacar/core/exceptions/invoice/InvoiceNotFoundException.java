package com.turkcell.rentacar.core.exceptions.invoice;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@SuppressWarnings("serial")
public class InvoiceNotFoundException extends BusinessException {

	public InvoiceNotFoundException(String message) {
		super(message);
	}

}
