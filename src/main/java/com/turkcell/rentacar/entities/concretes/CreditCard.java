package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int creditCarId;
	
	@Column(name="card_holder")
	private String cardHolder;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="cvv")
	private int cvv;
	
	@Column(name="month")
	private int month;
	
	@Column(name="year")
	private int year;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	
}
