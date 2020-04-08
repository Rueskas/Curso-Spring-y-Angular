package com.iessanvicente.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iessanvicente.springboot.backend.apirest.models.entity.Invoice;

public interface IInvoiceDao extends JpaRepository<Invoice, Long> {

}
