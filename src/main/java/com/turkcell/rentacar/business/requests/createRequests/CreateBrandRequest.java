package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBrandRequest {

	@NotEmpty
    private String brandName;
}
