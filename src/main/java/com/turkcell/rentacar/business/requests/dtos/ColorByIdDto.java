package com.turkcell.rentacar.business.requests.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorByIdDto {

    private int colorId;
    private String colorName;
}
