package com.dental.lab.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dental.lab.assemblers.AddressModelAssembler;
import com.dental.lab.data.Entities.AddressEntity;
import com.dental.lab.data.models.AddressModel;
import com.dental.lab.data.payloads.address.UpdateAddressPayload;
import com.dental.lab.services.AddressService;

@RestController
@RequestMapping(path = "/api/v1/addresses/")
public class AddressController {
	
	private static final Logger log = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressModelAssembler addressModelAssembler;
	
	@GetMapping(path = "/{addressId}")
	public ResponseEntity<AddressModel> findById(
			@PathVariable("addressId") Long addressId) {
		
		AddressEntity address = addressService.findByIdWithUser(addressId);
		
		return ResponseEntity.ok(
				addressModelAssembler.toModel(address));
		
	}
	
	@GetMapping
	public ResponseEntity<CollectionModel<AddressModel>> findAllForLoggedInUser() {
		Set<AddressEntity> addresses = addressService.findAllForLoggedInUser();
		
		CollectionModel<AddressModel> addressesModel = 
				addressModelAssembler.toCollectionModel(addresses);
		
		return ResponseEntity.ok(addressesModel);
	}
	
	@GetMapping(params = {"user_id"})
	public ResponseEntity<CollectionModel<AddressModel>> findByUserIdWithUser(
			@RequestParam("user_id") Long userId) {
		Set<AddressEntity> addresses = addressService.findAllForGivenUser(userId);
		
		CollectionModel<AddressModel> addressesModel = 
				addressModelAssembler.toCollectionModel(addresses);

		return ResponseEntity.ok(addressesModel);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<AddressModel> updateAddress(
			@RequestBody UpdateAddressPayload addressPayload,
			@PathVariable("id") Long addressId) {
		
		AddressEntity updatedAddress = 
				addressService.updateAddress(addressId, addressPayload);
		
		log.info("AddressEntity with id " + addressId + " has been updated successfully.");
		
		return ResponseEntity
				.ok(addressModelAssembler.toModel(updatedAddress));
		
	}

	public ResponseEntity<?> createAddress(
			@Valid @RequestBody AddressEntity address,
			@RequestParam(name = "user_id", required = true) Long userId) {
		
		
		return ResponseEntity
				.created(null)
				.build();
	}
	
	@PutMapping(path = "/{id}/set-default")
	public ResponseEntity<HttpStatus> setDefaultAddress(
			@PathVariable("id") Long addressId) {
		
		addressService.setAsDefaultAddress(addressId);
		
		log.info("AddressEntity with id " + addressId + " has been set as default address.");
		
		return ResponseEntity
				.noContent()
				.build();
	}

}
