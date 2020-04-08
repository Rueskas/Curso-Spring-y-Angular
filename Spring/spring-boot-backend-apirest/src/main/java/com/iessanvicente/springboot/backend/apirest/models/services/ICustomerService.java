package com.iessanvicente.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;
import com.iessanvicente.springboot.backend.apirest.models.entity.Invoice;
import com.iessanvicente.springboot.backend.apirest.models.entity.Region;

public interface ICustomerService {
	public List<Customer> findAll();
	public Page<Customer> findAll(Pageable pageable);
	public Customer findById(Long id);
	public Customer save(Customer customer);
	public void delete(Long id);
	public boolean existsById(Long id);
	public List<Region> findAllRegions();
	public Invoice findInvoiceById(Long id);
	public Invoice saveInvoice(Invoice invoice);
	public void deleteInvoiceById(Long id);
}
