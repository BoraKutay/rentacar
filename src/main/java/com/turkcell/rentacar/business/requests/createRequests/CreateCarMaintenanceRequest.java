package com.turkcell.rentacar.business.requests.createRequests;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	private String description;
	private LocalDate returnDate;
	
	private int carCarId;
}
