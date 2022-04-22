package com.turkcell.rentacar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    //bigdecimal
    @Column(name = "daily_price")
    private double dailyPrice;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "description")
    private String carDescription;
    
    
    @Column(name = "car_kilometer")
    private int carKilometer;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    
    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CarMaintenance> carMaintenances;
    
    @OneToMany(mappedBy = "car")
    private List<Rental> rentals;
    
    @OneToMany(mappedBy = "car")
    private List<CarDamage> carDamages;
    
    
    
}
