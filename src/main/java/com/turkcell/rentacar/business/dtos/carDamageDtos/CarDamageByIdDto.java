package com.turkcell.rentacar.business.dtos.carDamageDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageByIdDto {

	private int carCarId;	
	private String damageRecord;
}
