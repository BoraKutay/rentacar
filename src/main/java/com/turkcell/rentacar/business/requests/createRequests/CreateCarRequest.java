package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

	@PositiveOrZero
	@NotNull
    private double dailyPrice;
	
	@Min(1950)
	@NotNull
    private int modelYear;
	
    private String description;
    
    @NotNull
    @PositiveOrZero
    private int carKilometer;
    
    @NotNull
    @Positive
    private int brandId;
    
    @NotNull
    @Positive
    private int colorId;
}
