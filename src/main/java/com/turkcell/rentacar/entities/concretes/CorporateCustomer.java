package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "corporate_customer_id")
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {
   
	@Column(name = "corporate_customer_id",insertable = false,updatable = false)
	private int corporateCustomerId;
	
	@Column(name = "company_name")
    private String companyName;

    @Column(name = "tax_number")
    private String taxNumber;
}
