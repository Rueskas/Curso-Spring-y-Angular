package com.iessanvicente.springboot.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.iessanvicente.springboot.backend.apirest.models.entity.User;
import com.iessanvicente.springboot.backend.apirest.models.services.IUserService;

@Component
public class InfoAditionalToken implements TokenEnhancer {
	
	@Autowired
	private IUserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		User user = userService.findByName(authentication.getName());
		info.put("aditional_info", "Info ".concat(authentication.getName()));
		info.put("user_name", user.getUsername());
		info.put("name", user.getName());
		info.put("surname", user.getSurname());
		info.put("email", user.getEmail());
		info.put("enable", user.getEnabled());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
