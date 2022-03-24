package com.turkcell.rentacar.core.adapters.concretes;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
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
	public Result isCardValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {
		
		if(akbankPosService.isCardValid(createCreditCardRequest.getCardHolder(), createCreditCardRequest.getCardNumber(), createCreditCardRequest.getCvv(), createCreditCardRequest.getMonth(), createCreditCardRequest.getYear()))	{
			
			return new SuccessResult(BusinessMessages.CARD_IS_VALID);
		}

			throw new BusinessException(BusinessMessages.CARD_IS_NOT_VALID);
	}

	@Override
	public Result makePayment(CreateCreditCardRequest createCreditCardRequest,double totalPayment) throws BusinessException {
		

		if(akbankPosService.makePayment(createCreditCardRequest.getCardHolder(), createCreditCardRequest.getCardNumber(), createCreditCardRequest.getCvv(), createCreditCardRequest.getMonth(), createCreditCardRequest.getYear(),totalPayment)){
			
			return new SuccessResult(BusinessMessages.PAYMENT_SUCCEED);
		}

			throw new BusinessException(BusinessMessages.CARD_IS_NOT_VALID);
		
	}

}
