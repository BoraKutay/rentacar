package com.turkcell.rentacar.business.requests.deleteRequests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePaymentRequest {
	private int paymentId;
}
