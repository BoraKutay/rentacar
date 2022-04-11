package com.turkcell.rentacar.business.dtos.carMaintenanceDtos;


import java.time.LocalDate;

import com.turkcell.rentacar.business.dtos.carDtos.CarByIdDto;

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
	
	private CarByIdDto car;
    
}
