package com.iessanvicente.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
	
	private String avatar;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
