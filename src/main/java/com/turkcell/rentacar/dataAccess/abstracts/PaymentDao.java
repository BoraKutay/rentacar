package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {
}
