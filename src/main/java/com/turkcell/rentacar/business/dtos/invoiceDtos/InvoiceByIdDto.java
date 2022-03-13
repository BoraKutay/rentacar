package com.turkcell.rentacar.business.dtos.invoiceDtos;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceByIdDto {
    private int invoiceNo;

    private int rentalDayNumber;

    private double additionalPrice;

    private int rentalId;

    private int customerId;

    private LocalDate startDate;

    private LocalDate endDate;
}
