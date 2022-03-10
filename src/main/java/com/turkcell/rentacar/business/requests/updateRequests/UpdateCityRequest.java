package com.turkcell.rentacar.business.requests.updateRequests;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
    @NotNull
    @Positive
	private int cityId;
	
	@NotNull
	private String cityName;
}
