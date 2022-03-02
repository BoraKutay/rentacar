package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_maintenances")
public class CarMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int carMaintenanceId;
	
    @Column(name = "description")
	private String description;
	
    @Column(name = "return_date")
	private String returnDate;
    
 
    @OneToOne
    @JoinColumn(name="car_id")
    private Car car;
}