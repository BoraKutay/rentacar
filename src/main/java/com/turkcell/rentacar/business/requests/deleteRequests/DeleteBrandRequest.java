package com.turkcell.rentacar.business.requests.deleteRequests;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBrandRequest {
	
	@NotNull
	@Positive
	private int brandId;
}
