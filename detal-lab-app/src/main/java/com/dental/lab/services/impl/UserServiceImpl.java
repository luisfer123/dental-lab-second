package com.dental.lab.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dental.lab.data.Entities.AuthorityEntity;
import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.data.enums.ERole;
import com.dental.lab.data.payloads.security.SignupRequest;
import com.dental.lab.data.payloads.users.UpdateUserFullNamePayload;
import com.dental.lab.exceptions.EntityNotFoundException;
import com.dental.lab.repositories.AuthorityRepository;
import com.dental.lab.repositories.UserRepository;
import com.dental.lab.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthorityRepository authRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	@Transactional(readOnly = true)
	@PreAuthorize(value = "hasRole('ADMIN') or principal.id == #userId")
	public UserEntity findById(Long userId) throws EntityNotFoundException {
		return userRepo.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("UserEntity with id " + userId + " does not exists."));
	}
	
	@Override
	@Transactional(readOnly = true)
	@PreAuthorize(value = "hasRole('ADMIN') or principal.id == #userId")
	public UserEntity findByIdWithPhonesAndAddresses(Long userId) throws EntityNotFoundException {
		return userRepo.findByIdWithPhonesAndAddresses(userId)
			.orElseThrow(() -> new EntityNotFoundException("UserEntity with id " + userId + " does not exists."));
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserEntity findByUsernameWithAuthorities(String username) throws EntityNotFoundException {
		return userRepo.findByUsernameWithAuthorities(username)
				.orElseThrow(() -> new EntityNotFoundException("UserEntity with username " + username + " was not found."));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	@PreAuthorize(value = "hasRole('ADMIN')")
	public UserEntity registerNewUser(SignupRequest signupRequest) throws EntityNotFoundException {
		
		UserEntity user = new UserEntity(signupRequest.getUsername(),
				encoder.encode(signupRequest.getPassword()),
				signupRequest.getEmail());
		
		Set<String> strRoles = signupRequest.getRoles();
		Set<AuthorityEntity> authorities = new HashSet<>();
				
		if(strRoles == null || strRoles.size() == 0) {
			AuthorityEntity userAuth = authRepo.findByRole(ERole.ROLE_USER)
					.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_USER was not found."));
			authorities.add(userAuth);
		} else {
			
			if(!strRoles.contains("user")) {
				AuthorityEntity userAuth = authRepo.findByRole(ERole.ROLE_USER)
						.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_USER was not found."));
				authorities.add(userAuth);
			}
			
			strRoles.forEach(role -> {
				switch(role) {
				// If more Roles are added, add here the corresponding cases
				case "admin":
					AuthorityEntity adminAuth = authRepo.findByRole(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_ADMIN was not found."));
					authorities.add(adminAuth);
					break;
				case "client":
					AuthorityEntity clientAuth = authRepo.findByRole(ERole.ROLE_CLIENT)
							.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_CLIENT was not found."));
					authorities.add(clientAuth);
					break;
				case "technician":
					AuthorityEntity techAuth = authRepo.findByRole(ERole.ROLE_TECHNICIAN)
							.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_TECHNICIAN was not found."));
					authorities.add(techAuth);
					break;
				case "dentist":
					AuthorityEntity dentistAuth = authRepo.findByRole(ERole.ROLE_DENTIST)
							.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_DENTIST was not found."));
					authorities.add(dentistAuth);
					break;
				case "user":
					AuthorityEntity userAuth = authRepo.findByRole(ERole.ROLE_USER)
							.orElseThrow(() -> new EntityNotFoundException("Error: ROLE_USER was not found."));
					authorities.add(userAuth);
					break;
				}
			});
		}
		
		user.setAuthorities(authorities);
		
		return userRepo.save(user);
	}
	
	@Override
	@Transactional
	@PreAuthorize(value = "hasRole('ADMIN') or principal.id == #userId")
	public UserEntity updateUserFullName(
			Long userId, UpdateUserFullNamePayload payload)	throws EntityNotFoundException {
		
		UserEntity user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("UserEntity with id " + userId + " was not found."));
		
		user.setFirstName(payload.getFirstName());
		user.setMiddleName(payload.getMiddleName());
		user.setLastName(payload.getLastName());
		user.setSecondLastName(payload.getSecondLastName());
		
		return userRepo.save(user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByUsername(String username) {
		return userRepo.existsByUsername(username);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

}
