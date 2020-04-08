package com.iessanvicente.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double price;
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)	
	private Date createdAt;
	
	@JsonProperty("items")
	@ToString.Exclude
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="product")
	private Set<InvoiceItem> items = new HashSet<>();
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	@JsonIgnore
	public Set<InvoiceItem> getItems() {
		return items;
	}


	@JsonIgnore
	public void setItems(Set<InvoiceItem> items) {
		this.items = items;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
