package com.iessanvicente.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="regions")
public class Region implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@JsonIgnore
	@OneToMany(
			mappedBy="region",
			cascade=CascadeType.ALL,
			orphanRemoval=true)
	private Set<Customer> customers = new HashSet<Customer>();
	private static final long serialVersionUID = 1L;
	

}
