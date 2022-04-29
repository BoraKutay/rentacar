package com.turkcell.rentacar.business.outServices;

import java.util.Random;

import org.springframework.stereotype.Service;


@Service
public class ZiraatBankPosService{
	

	public boolean isCardValid(String cardNumber, int cvv, int month, int year,String cardHolder){
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 65) {
			return false;
		}
		
		return true;
	}

	public boolean makePayment(String cardNumber, int cvv, int month, int year,String cardHolder,double totalPayment){
		
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 65) {
			return false;
		}
		
		return true;
	}

}
