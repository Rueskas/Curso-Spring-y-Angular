package com.iessanvicente.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {
	
}
