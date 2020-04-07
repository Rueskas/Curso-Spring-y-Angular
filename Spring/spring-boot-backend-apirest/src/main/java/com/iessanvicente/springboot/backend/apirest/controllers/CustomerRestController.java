package com.iessanvicente.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;
import com.iessanvicente.springboot.backend.apirest.models.entity.Region;
import com.iessanvicente.springboot.backend.apirest.models.services.ICustomerService;
import com.iessanvicente.springboot.backend.apirest.models.services.IUploadFileService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CustomerRestController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IUploadFileService uploadService;

	@GetMapping("/customers")
	public List<Customer> index() {
		return customerService.findAll();
	}

	@GetMapping("/customers/page/{page}")
	public Page<Customer> page(@PathVariable Integer page) {
		return customerService.findAll(PageRequest.of(page, 5));
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/customers/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Customer customer = null;
		Map<String, Object> response = new HashMap<>();
		try {
			customer = customerService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Error searching customer");
			response.put("error", e.getMostSpecificCause().getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (customer == null) {
			response.put("message", "Customer with ID: " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		response.put("message", "Customer found successfully");
		response.put("customer", customer);
		return ResponseEntity.ok().body(response);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/customers")
	public ResponseEntity<?> insert(@Valid @RequestBody Customer customer, BindingResult result) {
		Customer customerSaved = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			return returnErrors(result);
		}

		try {
			customerSaved = customerService.save(customer);
		} catch (DataAccessException e) {
			response.put("message", "Error creating customer");
			response.put("error", e.getMostSpecificCause().getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		response.put("message", "Customer created successfully");
		response.put("customer", customerSaved);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}
	@Secured("ROLE_ADMIN")
	@PutMapping("/customers/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Customer customer, BindingResult result,
			@PathVariable Long id) {
		Customer currentCustomer = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			return returnErrors(result);
		}

		try {
			currentCustomer = customerService.findById(id);

			if (currentCustomer != null) {
				currentCustomer.setName(customer.getName());
				currentCustomer.setSurname(customer.getSurname());
				currentCustomer.setEmail(customer.getEmail());
				currentCustomer.setRegion(customer.getRegion());
				Customer saved = customerService.save(currentCustomer);
				if (saved != null) {
					response.put("message", "Customer updated successfully");
					response.put("customer", saved);
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
				} else {
					response.put("message", "Error updating customer");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
				}
			} else {
				response.put("message", "Customer with ID: " + id + " not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (DataAccessException e) {
			response.put("message", "Error updating customer");
			response.put("error", e.getMostSpecificCause().getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		if (customerService.existsById(id)) {
			try {
				Customer customer = customerService.findById(id);
				this.uploadService.delete(customer.getAvatar());
				customerService.delete(id);
			} catch (DataAccessException e) {
				response.put("message", "Error deleting customer");
				response.put("error", e.getMostSpecificCause().getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			response.put("message", "Customer deleted successfully");
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "Customer not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("customers/regions")
	public List<Region> getRegions(){
		return customerService.findAllRegions();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/customers/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		Customer customer = null;
		if (file.isEmpty()) {
			response.put("message", "File is empty");
			return ResponseEntity.badRequest().body(response);
		} else {
			String filename = null;
			customer = customerService.findById(id);
			if (customer == null) {
				response.put("message", "Customer not exists");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			try {
				filename = uploadService.copy(file);
			} catch (IOException e) {
				response.put("message", "Error uploading image");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}

			this.uploadService.delete(customer.getAvatar());

			customer.setAvatar(filename);
			customerService.save(customer);
			response.put("message", "Avatar uploaded successfully");
			response.put("customer", customer);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/uploads/img/{filename:.+}")
	public ResponseEntity<?> getAvatar(@PathVariable String filename) {
		Map<String, Object> response = new HashMap<>();
		
		Resource resource;
		
		try {
			resource = this.uploadService.load(filename);
		} catch (MalformedURLException e) {
			response.put("error", "Error searching");
			response.put("message", "Image can not to be found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
	}

	public ResponseEntity<?> returnErrors(BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
		response.put("errors", errors);
		return ResponseEntity.badRequest().body(response);
	}

}
