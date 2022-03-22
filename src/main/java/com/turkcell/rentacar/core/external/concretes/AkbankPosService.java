package com.turkcell.rentacar.core.external.concretes;

import java.util.Random;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.core.external.abstracts.PosService;

@Service
public class AkbankPosService implements PosService {

	
	public AkbankPosService() {
		super();
	}

	@Override
	public boolean isCardValid(String cardHolder, String cardNumber, int cvv, int month, int year){
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 95) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isPaymentSuccess(double totalPayment){
		
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 95) {
			return false;
		}
		
		return true;
	}

}
