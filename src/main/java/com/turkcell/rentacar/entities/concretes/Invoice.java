package com.turkcell.rentacar.entities.concretes;


import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@OneToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
