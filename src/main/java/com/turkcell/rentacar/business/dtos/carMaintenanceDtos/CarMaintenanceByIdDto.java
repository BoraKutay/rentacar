package com.turkcell.rentacar.business.dtos.carMaintenanceDtos;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceByIdDto {

	private int carMaintenanceId;
	private String description;
	private LocalDate returnDate;
	
	private int carCarId;
    
}
