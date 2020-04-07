package com.iessanvicente.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iessanvicente.springboot.backend.apirest.models.entity.User;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);
	
	@Query("select u from User u where u.username = ?1")
	public User queryUsername(String username);
}
