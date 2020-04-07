package com.iessanvicente.springboot.backend.apirest.models.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iessanvicente.springboot.backend.apirest.models.dao.IUserDao;
import com.iessanvicente.springboot.backend.apirest.models.entity.User;

@Service
public class UserService implements UserDetailsService, IUserService {

	@Autowired
	private IUserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null) {
			String errorMsg = "Login Error: User not exists";
			logger.error(errorMsg);
			throw new UsernameNotFoundException(errorMsg);
		}
		System.out.println(user.getAuthorities());
		return user;
	}

	@Override
	public User findByName(String username) {
		return userDao.queryUsername(username);
	}

}
