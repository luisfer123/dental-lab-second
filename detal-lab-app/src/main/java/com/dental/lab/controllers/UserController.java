package com.dental.lab.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.data.models.UserModel;
import com.dental.lab.data.payloads.users.UpdateUserFullNamePayload;
import com.dental.lab.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private com.dental.lab.assemblers.UserModelAssembler  userModelAssembler;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<UserModel> findById(@PathVariable("id") Long userId) {
		
		UserEntity userEntity = userService.findByIdWithPhonesAndAddresses(userId);
		
		return ResponseEntity
				.ok(userModelAssembler.toModel(userEntity));
	}
	
	@PutMapping(path = "/{id}/full-name")
	public ResponseEntity<UserModel> updateUserFullName(
			@Valid @RequestBody UpdateUserFullNamePayload payload,
			@PathVariable("id") Long userId) {
		
		UserEntity user = userService.updateUserFullName(userId, payload);
		
		return new ResponseEntity<UserModel>(
				userModelAssembler.toModel(user), HttpStatus.ACCEPTED);
		
	}

}
