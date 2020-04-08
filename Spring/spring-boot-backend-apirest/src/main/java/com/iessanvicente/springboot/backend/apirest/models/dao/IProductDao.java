package com.iessanvicente.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iessanvicente.springboot.backend.apirest.models.entity.Product;

public interface IProductDao extends  JpaRepository<Product, Long>{
	public List<Product> findByNameContainsIgnoreCase(String name);
}
