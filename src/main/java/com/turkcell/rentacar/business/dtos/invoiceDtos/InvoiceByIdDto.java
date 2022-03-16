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

    private double billingPrice;

    private int rentalId;

    private int customerCustomerId;

    private LocalDate startDate;

    private LocalDate endDate;
    
    private LocalDate billingDate;
}
