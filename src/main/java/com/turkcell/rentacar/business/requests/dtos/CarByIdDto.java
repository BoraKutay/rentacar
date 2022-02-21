package com.turkcell.rentacar.business.requests.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarByIdDto {

    private int carId;
    private double dailyPrice;
    private int modelYear;
    private String description;
    private String brandName;
    private String colorName;
}
