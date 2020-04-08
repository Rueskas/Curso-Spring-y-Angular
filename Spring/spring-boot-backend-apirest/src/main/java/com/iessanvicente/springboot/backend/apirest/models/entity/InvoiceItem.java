package com.iessanvicente.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoice_item")
public class InvoiceItem implements Serializable{
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;

	private int amount;
	
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="invoice_id")
	private Invoice invoice;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "items"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	public Double getPrice() {
		return product.getPrice() * amount;
	}

	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public int getAmount() {
		return amount;
	}




	public void setAmount(int amount) {
		this.amount = amount;
	}



	@JsonIgnore
	public Invoice getInvoice() {
		return invoice;
	}



	@JsonIgnore
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}




	public Product getProduct() {
		return product;
	}




	public void setProduct(Product product) {
		this.product = product;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
