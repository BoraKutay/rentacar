package com.turkcell.rentacar.business.requests.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceByIdDto {

	private int carMaintenanceId;
	private String description;
	private String returnDate;
	private int carId;
    
}
