package com.iessanvicente.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iessanvicente.springboot.backend.apirest.models.dao.ICustomerDao;
import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;

//@RequiredArgsConstructor 
@Service
public class CustomerServiceImpl implements ICustomerService {

	//Con @RequiredArgsConstructor de Lombok no es necesario las anotaciones Autowired
	@Autowired
	private ICustomerDao customerDao;
	
	//Se puede omitir Transaccional por que ya está en CrudRepository
	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return (List<Customer>) customerDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		return customerDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Customer save(Customer customer) {
		return (Customer) customerDao.save(customer);	
	}

	@Override
	@Transactional
	public void delete(Long id) {
		customerDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(Long id) {
		return customerDao.existsById(id);
		
	}

}
