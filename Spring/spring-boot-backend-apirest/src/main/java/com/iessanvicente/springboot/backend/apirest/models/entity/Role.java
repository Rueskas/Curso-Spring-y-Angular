package com.iessanvicente.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements Serializable, GrantedAuthority {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, length=20)
	private String name;
	
	@ManyToMany(mappedBy="roles")
	List<User> users = new ArrayList<User>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String getAuthority() {
		return new SimpleGrantedAuthority(name).getAuthority();
	}

}
