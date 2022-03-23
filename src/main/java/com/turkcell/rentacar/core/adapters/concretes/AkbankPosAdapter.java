package com.turkcell.rentacar.core.adapters.concretes;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.core.adapters.abstracts.PosAdapterService;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.external.concretes.AkbankPosService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;


@Service
@Primary
public class AkbankPosAdapter implements PosAdapterService {

	AkbankPosService akbankPosService = new AkbankPosService();
	
	@Override
	public Result isCardValid(String cardHolder, String cardNumber, int cvv, int month, int year) throws BusinessException {
		
		if(akbankPosService.isCardValid(cardHolder, cardNumber, cvv, month, year))	{
			
			return new SuccessResult(BusinessMessages.CARD_IS_VALID);
		}

			throw new BusinessException(BusinessMessages.CARD_IS_NOT_VALID);
	}

	@Override
	public Result isPaymentSuccess(double totalPayment) throws BusinessException {
		

		if(akbankPosService.isPaymentSuccess(totalPayment)){
			
			return new SuccessResult(BusinessMessages.PAYMENT_SUCCEED);
		}

			throw new BusinessException(BusinessMessages.CARD_IS_NOT_VALID);
		
	}

}
