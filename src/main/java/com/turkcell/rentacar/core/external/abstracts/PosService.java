package com.turkcell.rentacar.core.external.abstracts;



public interface PosService {
	
	public boolean isCardValid(String cardHolder, String cardNumber, int cvv, int month, int year);
	public boolean isPaymentSuccess(double totalPayment);
}


