package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rentals")
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_id")
	private int rentalId;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Column(name = "additional_price")
	private double additionalPrice;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	
    @OneToMany(mappedBy = "rental")
    private List<OrderedAdditionalService> orderedAdditionalServices;
    
    @ManyToOne
    @JoinColumn(name="city_pick_up_id")
    private City cityOfPickUpLocation;
    
    
    @ManyToOne
    @JoinColumn(name="city_return_id")
    private City cityOfReturnLocation;
    
    @OneToOne(mappedBy = "rental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    
}
