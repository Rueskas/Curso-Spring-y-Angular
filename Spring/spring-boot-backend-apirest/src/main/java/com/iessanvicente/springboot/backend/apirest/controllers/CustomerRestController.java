package com.iessanvicente.springboot.backend.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;
import com.iessanvicente.springboot.backend.apirest.models.services.ICustomerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= {"http://localhost:4200"})
public class CustomerRestController {

	@Autowired
	private ICustomerService customerService;
	
	private final Logger log = LoggerFactory.getLogger(CustomerRestController.class);
	
	@GetMapping("/customers")
	public List<Customer> index(){
		return customerService.findAll();
	}
	
	@GetMapping("/customers/page/{page}")
	public Page<Customer> page(@PathVariable Integer page){
		return customerService.findAll(PageRequest.of(page, 5));
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Customer customer = null;
		Map<String, Object> response = new HashMap<>();
		try {
			customer = customerService.findById(id);
		} catch(DataAccessException e) {
			response.put("message", "Error searching customer");
			response.put("error", e.getMostSpecificCause().getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if(customer == null) {
			response.put("message", "Customer with ID: "+ id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		response.put("message", "Customer found successfully");
		response.put("customer", customer);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/customers")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> insert(@Valid @RequestBody Customer customer, BindingResult result) {
		Customer customerSaved = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
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
		response.put("customer",customerSaved);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<?> update( @Valid @RequestBody Customer customer, BindingResult result, @PathVariable Long id) {
		Customer currentCustomer = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			return returnErrors(result);
		}
		
		try {
			currentCustomer = customerService.findById(id);
			
			if(currentCustomer != null) {
				currentCustomer.setName(customer.getName());
				currentCustomer.setSurname(customer.getSurname());
				currentCustomer.setEmail(customer.getEmail());
				Customer saved = customerService.save(currentCustomer);
				if(saved != null) {
					response.put("message", "Customer updated successfully");
					response.put("customer", saved);
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
				} else {
					response.put("message", "Error updating customer");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
				}
			} else {
				response.put("message", "Customer with ID: "+ id + " not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch(DataAccessException e) {
			response.put("message", "Error updating customer");
			response.put("error", e.getMostSpecificCause().getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		if(customerService.existsById(id)) {
			try {
				Customer customer = customerService.findById(id);
				deleteAvatarBefore(customer);
				customerService.delete(id);
			} catch(DataAccessException e) {
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
	
	@PostMapping("/customers/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		Customer customer = null;
		if(file.isEmpty()) {
			response.put("message", "File is empty");
			return ResponseEntity.badRequest().body(response);
		} else {

			try {
				customer = customerService.findById(id);
			} catch(DataAccessException e) {
				response.put("message", "Error finding customer with ID: "+ id );
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			if(customer == null) {
				response.put("message", "Customer with ID: " + id + " not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			
			String filename = UUID.randomUUID().toString() + "_"+file.getOriginalFilename().replace(" ", "_");
			Path path = Paths.get("upload-dir").resolve(filename).toAbsolutePath();

			log.info(path.toString());
			try {
				Files.copy(file.getInputStream(),path);
			} catch(IOException e) {
				response.put("message", "Error uploading avatar");
				response.put("error", e.getCause().getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			
			deleteAvatarBefore(customer);
			
			customer.setAvatar(filename);
			customerService.save(customer);
			response.put("message", "Avatar uploaded successfully");
			response.put("customer", customer);
			return ResponseEntity.ok(response);
			
			
		}
	}
	
	@GetMapping("/uploads/img/{filename:.+}")
	public ResponseEntity<?> getAvatar(@PathVariable String filename){
		Map<String, Object> response = new HashMap<>();
		try {
			Path pathFile = Paths.get("upload-dir").resolve(filename).toAbsolutePath();
			log.info(pathFile.toString());
			Resource resource;
			try {
				resource = new UrlResource(pathFile.toUri());
				if(!resource.exists() || !resource.isReadable()) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not exists or can not be readed");
				}
			} catch (MalformedURLException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not exists or can not be readed");
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()+"\"");
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
		} catch(DataAccessException e) {
			response.put("error", "Error searching image");
			response.put("message", e.getMostSpecificCause().getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	public ResponseEntity<?> returnErrors(BindingResult result){
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		errors = result.getFieldErrors().stream()
				.map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());
		response.put("errors", errors);
		return ResponseEntity.badRequest().body(response);
	}
	
	public void deleteAvatarBefore(Customer customer) {
		String avatarBefore = customer.getAvatar();
		if(avatarBefore != null && !avatarBefore.isEmpty()) {
			Path pathAvatarBefore = Paths.get("upload-dir").resolve(avatarBefore).toAbsolutePath();
			File fileBefore = pathAvatarBefore.toFile();
			if(fileBefore.exists() && fileBefore.canRead()) {
				fileBefore.delete();
			}
		}
	}
}
