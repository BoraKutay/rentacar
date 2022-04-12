package com.turkcell.rentacar.business.requests.updateRequests;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	@NotNull
	@Positive
	private int invoiceNo;
	
	@NotNull
	@Positive
	private int rentalId;
		
}
