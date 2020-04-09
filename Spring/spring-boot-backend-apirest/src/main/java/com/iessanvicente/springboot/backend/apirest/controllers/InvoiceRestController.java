package com.iessanvicente.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iessanvicente.springboot.backend.apirest.models.entity.Invoice;
import com.iessanvicente.springboot.backend.apirest.models.services.CustomerServiceImpl;

@RestController
@CrossOrigin(origins= {"http://localhost:4200", "*"})
@RequestMapping("/api")
public class InvoiceRestController {

	@Autowired
	private CustomerServiceImpl customerService;
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/invoices/{id}")
	public ResponseEntity<?> getInvoice(@PathVariable Long id) {
		Invoice invoice =  customerService.findInvoiceById(id);	
		Map<String, Object> response = new HashMap<>();
		if(invoice != null) {
			return ResponseEntity.ok(invoice);
		} else {
			response.put("message", "Invoice not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/invoices/{id}")
	public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
		customerService.deleteInvoiceById(id);
		return ResponseEntity.noContent().build();
	}
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/invoices/filter-products/{filter}")
	public ResponseEntity<?> findProducts(@PathVariable String filter){
		return ResponseEntity.ok(customerService.findProductsStartingWith(filter));
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/invoices")
	public ResponseEntity<?> postInvoice(@RequestBody Invoice invoice){
		Invoice saved = customerService.saveInvoice(invoice);
		System.out.println(invoice);
		System.out.println(saved);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}
