package com.iessanvicente.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iessanvicente.springboot.backend.apirest.models.dao.ICustomerDao;
import com.iessanvicente.springboot.backend.apirest.models.dao.IInvoiceDao;
import com.iessanvicente.springboot.backend.apirest.models.dao.IProductDao;
import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;
import com.iessanvicente.springboot.backend.apirest.models.entity.Invoice;
import com.iessanvicente.springboot.backend.apirest.models.entity.Product;
import com.iessanvicente.springboot.backend.apirest.models.entity.Region;

//@RequiredArgsConstructor 
@Service
public class CustomerServiceImpl implements ICustomerService {

	//Con @RequiredArgsConstructor de Lombok no es necesario las anotaciones Autowired
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IInvoiceDao invoiceDao;
	@Autowired
	private IProductDao productDao;
	
	//Se puede omitir Transaccional por que ya está en CrudRepository
	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return (List<Customer>) customerDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Customer> findAll(Pageable pageable) {
		return customerDao.findAll(pageable);
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

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegions() {
		return customerDao.findAllRegions();
	}

	@Override
	public Invoice findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	@Override
	public Invoice saveInvoice(Invoice invoice) {
		return invoiceDao.save(invoice);
	}

	@Override
	public void deleteInvoiceById(Long id) {
		invoiceDao.deleteById(id);
	}

	@Override
	public List<Product> findProductsStartingWith(String sentence) {
		return productDao.findByNameContainsIgnoreCase(sentence);
		
	}


}
