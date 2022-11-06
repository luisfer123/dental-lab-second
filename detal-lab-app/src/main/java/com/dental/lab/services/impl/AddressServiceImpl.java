package com.dental.lab.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dental.lab.data.Entities.AddressEntity;
import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.data.payloads.address.UpdateAddressPayload;
import com.dental.lab.exceptions.EntityNotFoundException;
import com.dental.lab.repositories.AddressRepository;
import com.dental.lab.security.UserDetailsImpl;
import com.dental.lab.services.AddressService;
import com.dental.lab.services.UserService;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = true)
	@PreAuthorize(value = "hasRole('USER')")
	@PostAuthorize(value = "hasRole('ADMIN') or principal.id == #returnObject.user.id")
	public AddressEntity findByIdWithUser(Long addressId) 
			throws EntityNotFoundException {
		
		return addressRepo.findByIdWithUser(addressId)
				.orElseThrow(() -> new EntityNotFoundException("Address Entity with id " + addressId + " was not found."));
	}
	
	@Override
	@Transactional(readOnly = true)
	@PreAuthorize(value = "hasRole('USER')")
	@PostAuthorize(value = "hasRole('ADMIN') or principal.id == #returnObject.user.id")
	public AddressEntity findById(Long addressId) 
			throws EntityNotFoundException {
		
		return addressRepo.findById(addressId)
				.orElseThrow(() -> new EntityNotFoundException("Address Entity with id " + addressId + " was not found."));
	}
	
	@Override
	@Transactional(readOnly = true)
	@PreAuthorize(value = "hasRole('USER')")
	public Set<AddressEntity> findAllForLoggedInUser() {
		UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		return addressRepo.findByUserIdWithUser(principal.getId());
	}
	
	@Override
	@Transactional(readOnly = true)
	@PreAuthorize(value = "hasRole('ADMIN') or principal.id == #userId")
	// @PostAuthorize(value = "hasRole('ADMIN') or (#returnObject.length > 0 ? #returnObject[0].user.id = principal.id : true)")
	public Set<AddressEntity> findAllForGivenUser(Long userId) {
		
		return addressRepo.findByUserIdWithUser(userId);
	}
	
	@Override
	@Transactional
	@PreAuthorize(value = "hasRole('USER')")
	public AddressEntity updateAddress(Long addressId, UpdateAddressPayload addressData)
			throws EntityNotFoundException {
		
		AddressEntity address = 
				this.findByIdWithUser(addressId);

		address.setNumber(addressData.getNumber());
		address.setInnerNumber(addressData.getInnerNumber());
		address.setStreet(addressData.getStreet());
		address.setCity(addressData.getCity());
		address.setZipPostcode(addressData.getZipPostcode());
		address.setAddressType(addressData.getAddressType());
		
		
		return addressRepo.save(address);
	}
	
	@Override
	@Transactional
	@PreAuthorize(value = "hasRole('USER')")
	public void setAsDefaultAddress(Long addressId) throws EntityNotFoundException {
		
		AddressEntity address = 
				this.findByIdWithUser(addressId);
		
		UserEntity user = address.getUser();
		user.setDefaultAddressId(address.getId());
		
		userService.save(user);
	}
	
	@Override
	@Transactional
	@PreAuthorize(value = "hasRole('ADMIN') or principal.id == #userId")
	public AddressEntity saveAddress(AddressEntity address, Long userId)
			throws EntityNotFoundException {
		
		UserEntity user = userService.findById(userId);
		user.addAddress(address);
		
		AddressEntity savedAddress = addressRepo.save(address);
		
		return savedAddress;
	}

}
