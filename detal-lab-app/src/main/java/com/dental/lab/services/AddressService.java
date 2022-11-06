package com.dental.lab.services;

import java.util.Set;

import com.dental.lab.data.Entities.AddressEntity;
import com.dental.lab.data.payloads.address.UpdateAddressPayload;
import com.dental.lab.exceptions.EntityNotFoundException;

public interface AddressService {
	
	AddressEntity findById(Long addressId) throws EntityNotFoundException;
	
	AddressEntity findByIdWithUser(Long addressId) throws EntityNotFoundException;
	
	Set<AddressEntity> findAllForLoggedInUser();
	
	Set<AddressEntity> findAllForGivenUser(Long userId);
	
	AddressEntity saveAddress(AddressEntity address, Long userId) throws EntityNotFoundException;
	
	AddressEntity updateAddress(Long addressId, UpdateAddressPayload addressData) throws EntityNotFoundException;
	
	void setAsDefaultAddress(Long addressId) throws EntityNotFoundException;

}
