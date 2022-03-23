package com.turkcell.rentacar.core.external.concretes;

import java.util.Random;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.core.external.abstracts.PosService;


@Service
@Qualifier("ZiraatBank")
public class ZiraatBankPosService implements PosService {
	
	public ZiraatBankPosService() {
		super();
	}

	@Override
	public boolean isCardValid(String cardHolder, String cardNumber, int cvv, int month, int year){
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 65) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isPaymentSuccess(double totalPayment){
		
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 65) {
			return false;
		}
		
		return true;
	}

}
