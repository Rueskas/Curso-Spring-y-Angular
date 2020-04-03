package com.iessanvicente.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;
import com.iessanvicente.springboot.backend.apirest.models.services.ICustomerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= {"http://localhost:4200"})
public class CustomerRestController {

	@Autowired
	private ICustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> index(){
		return customerService.findAll();
	}
	
	@GetMapping("/customers/{id}")
	public Customer show(@PathVariable Long id) {
		return customerService.findById(id);
	}
	
	@PostMapping("/customers")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer insert(@RequestBody Customer customer) {
		return customerService.save(customer);
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<?> update(@RequestBody Customer customer, @PathVariable Long id) {
		Customer currentCustomer = customerService.findById(id);
		if(customer != null) {
			currentCustomer.setName(customer.getName());
			currentCustomer.setSurname(customer.getSurname());
			currentCustomer.setEmail(customer.getEmail());
			return ResponseEntity.ok(customerService.save(currentCustomer));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		if(customerService.existsById(id)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
