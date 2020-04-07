package com.iessanvicente.springboot.backend.apirest.models.services;

import com.iessanvicente.springboot.backend.apirest.models.entity.User;

public interface IUserService {
	public User findByName(String username);
}
