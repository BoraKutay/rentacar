package com.turkcell.rentacar.entities.concretes;


import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_no")
	private int invoiceNo;
	
	@Column(name = "rental_day_number")
	private int rentalDayNumber;
	
	@Column(name = "start_date_rental")
	private LocalDate startDateRental;
	
	@Column(name = "end_date_rental")
	private LocalDate endDateRental;
	
	@Column(name = "billing_date")
	private LocalDate billingDate;
	
	@Column(name= "billing_price")
	private double billingPrice;
	
	@OneToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	//denormalize edilmesi gereken alan
}
