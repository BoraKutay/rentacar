package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
    List<Car> getByDailyPriceIsLessThanEqual(double dailyPrice);
    List<Car> getByModelYearIsLessThanEqual(int modelYear);
}
