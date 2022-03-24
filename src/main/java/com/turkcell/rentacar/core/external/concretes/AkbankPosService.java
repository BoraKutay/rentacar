package com.turkcell.rentacar.core.external.concretes;

import java.util.Random;

import org.springframework.stereotype.Service;


@Service
public class AkbankPosService{


	public boolean isCardValid(String cardHolder, String cardNumber, int cvv, int month, int year){
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 95) {
			return false;
		}
		
		return true;
	}

	public boolean makePayment(String cardHolder, String cardNumber, int cvv, int month, int year, double totalPayment){
		
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 5) {
			return false;
		}
		
		return true;
	}

}
