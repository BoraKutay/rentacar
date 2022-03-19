package com.turkcell.rentacar.business.requests.updateRequests;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest {
	
	@NotNull
	@PositiveOrZero
	private int paymentId;
	
	@NotNull
	@PositiveOrZero
	private int rentalId;

}
