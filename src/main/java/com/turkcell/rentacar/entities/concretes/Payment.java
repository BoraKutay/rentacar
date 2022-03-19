package com.turkcell.rentacar.entities.concretes;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "payments")
public class Payment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
    
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    
    @OneToMany(mappedBy = "payment")
    private List<OrderedAdditionalService> orderedAdditionalServices;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
