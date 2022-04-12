package com.turkcell.rentacar.business.requests.deleteRequests;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int additionalServiceId;
}
