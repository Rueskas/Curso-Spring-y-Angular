package com.iessanvicente.springboot.backend.apirest.models.services;

import java.util.List;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;

public interface ICustomerService {
	public List<Customer> findAll();
	public Customer findById(Long id);
	public Customer save(Customer customer);
	public void delete(Long id);
	public boolean existsById(Long id);
}
