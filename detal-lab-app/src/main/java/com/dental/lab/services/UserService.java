package com.dental.lab.services;

import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.data.payloads.security.SignupRequest;
import com.dental.lab.data.payloads.users.UpdateUserFullNamePayload;
import com.dental.lab.exceptions.EntityNotFoundException;

public interface UserService {
	
	UserEntity findById(Long userId) throws EntityNotFoundException;
	
	UserEntity findByIdWithPhonesAndAddresses(Long userId) throws EntityNotFoundException;
	
	UserEntity findByUsernameWithAuthorities(String username) throws EntityNotFoundException;
	
	/**
	 * 
	 * @param signupRequest
	 * @return
	 * @throws EntityNotFoundException
	 */
	UserEntity registerNewUser(SignupRequest signupRequest) throws EntityNotFoundException;
	
	UserEntity updateUserFullName(
			Long userId, UpdateUserFullNamePayload payload)	throws EntityNotFoundException;
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	UserEntity save(UserEntity user);

}
