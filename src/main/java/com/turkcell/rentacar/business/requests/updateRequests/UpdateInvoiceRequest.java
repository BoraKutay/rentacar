package com.turkcell.rentacar.business.requests.updateRequests;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	private int invoiceNo;
	private int rentalId;
		
}
