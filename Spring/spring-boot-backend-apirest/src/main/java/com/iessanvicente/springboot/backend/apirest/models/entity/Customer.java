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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
public class Customer implements Serializable {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@NotEmpty(message="Name can not to be empty")
	@Size(message="Name length has to be between 4 and 12", min=4, max=12)
	private String name;
	@NotEmpty(message="Surname can not to be empty")
	private String surname;
	@NotEmpty(message="Email can not to be empty")
	@Email(message="Email has not correct format")
	@Column(nullable = false, unique = false)
	private String email;
	
	@NotNull(message="Date can not to be empty")
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@NotNull(message="Region can not be empty")
	private Region region;
	
	private String avatar;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "customer"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy="customer", cascade = CascadeType.ALL)
	private Set<Invoice> invoices = new HashSet<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
