package com.dental.lab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.services.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = null;
		
		try {
			user = userService.findByUsernameWithAuthorities(username);
		} catch(Exception e) {
			throw new UsernameNotFoundException(username);
		}
		
		return UserDetailsImpl.build(user);
	}

}
