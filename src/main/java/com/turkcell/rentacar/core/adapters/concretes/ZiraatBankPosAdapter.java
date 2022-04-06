package com.turkcell.rentacar.core.adapters.concretes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.createRequests.CreateCreditCardRequest;
import com.turkcell.rentacar.core.adapters.abstracts.PosAdapterService;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.external.concretes.ZiraatBankPosService;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;

@Service
@Qualifier("ZiraatBank")
public class ZiraatBankPosAdapter implements PosAdapterService {
	
	ZiraatBankPosService ziraatBankPosService = new ZiraatBankPosService();
	
	@Override
	public Result isCardValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {
		
		if(ziraatBankPosService.isCardValid(createCreditCardRequest.getCardNumber(), createCreditCardRequest.getCvv(), createCreditCardRequest.getMonth(), createCreditCardRequest.getYear(),createCreditCardRequest.getCardHolder()))	{
			
			return new SuccessResult(BusinessMessages.CARD_IS_VALID);
		}

			throw new BusinessException(BusinessMessages.CARD_IS_NOT_VALID);
	}

	@Override
	public Result makePayment(CreateCreditCardRequest createCreditCardRequest, double totalPayment) throws BusinessException {
		

		if(ziraatBankPosService.makePayment(createCreditCardRequest.getCardNumber(), createCreditCardRequest.getCvv(), createCreditCardRequest.getMonth(), createCreditCardRequest.getYear(),createCreditCardRequest.getCardHolder(),totalPayment)){
			
			return new SuccessResult(BusinessMessages.PAYMENT_SUCCEED);
		}

			throw new BusinessException(BusinessMessages.CARD_IS_NOT_VALID);
		
	}
}
