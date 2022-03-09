package com.turkcell.rentacar.business.dtos;




import java.util.List;

import com.turkcell.rentacar.entities.concretes.AdditionalService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceByIdDto {
	
	private int orderedAdditionalServiceId;
	
	 private List<AdditionalService> AdditionalServices;
	
}
