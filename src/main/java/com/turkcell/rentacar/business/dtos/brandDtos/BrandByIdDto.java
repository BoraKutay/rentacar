package com.turkcell.rentacar.business.dtos.brandDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandByIdDto {

    private int brandId;
    private String brandName;
}
