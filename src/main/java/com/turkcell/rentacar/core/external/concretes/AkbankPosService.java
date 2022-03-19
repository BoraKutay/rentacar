package com.turkcell.rentacar.core.external.concretes;

import java.util.Random;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.external.abstracts.PosService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;

@Service
public class AkbankPosService implements PosService {

	
	public AkbankPosService() {
		super();
	}

	@Override
	public Result isCardValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 95) {
			throw new BusinessException("Card is not valid");
		}
		
		return new SuccessResult("Success");
	}

	@Override
	public Result isPaymentSuccess(double totalPayment) throws BusinessException {
		
		Random isSuccess = new Random();
		int num = isSuccess.nextInt(100);
		
		if(num > 95) {
			throw new BusinessException("Not enough money");
		}
		
		return new SuccessResult("Payment success");
	}

}
