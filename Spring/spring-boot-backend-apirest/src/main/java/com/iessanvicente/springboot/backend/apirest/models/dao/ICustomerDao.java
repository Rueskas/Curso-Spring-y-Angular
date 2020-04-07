package com.iessanvicente.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iessanvicente.springboot.backend.apirest.models.entity.Customer;
import com.iessanvicente.springboot.backend.apirest.models.entity.Region;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {
	
	@Query("from Region")
	public List<Region> findAllRegions();
}
