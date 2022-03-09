package com.turkcell.rentacar.business.requests.updateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalServiceRequest {
	private int additionalServiceId;

    private String additionalServiceName;

    private double dailyPrice;
}
