package com.dental.lab.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.dental.lab.controllers.UserController;
import com.dental.lab.data.Entities.AddressEntity;
import com.dental.lab.data.Entities.PhoneEntity;
import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.data.models.AddressModel;
import com.dental.lab.data.models.PhoneModel;
import com.dental.lab.data.models.UserModel;
import com.dental.lab.helpers.ImageHelper;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserEntity, UserModel> {
	
	@Autowired
	private ResourceLoader resourceLoader;

	public UserModelAssembler(Class<?> controllerClass, Class<UserModel> resourceType) {
		super(controllerClass, resourceType);
	}
	
	public UserModelAssembler() {
		this(UserController.class, UserModel.class);
	}

	@Override
	public UserModel toModel(UserEntity userEntity) {
		
		UserModel userModel = instantiateModel(userEntity);
		
		userModel.add(linkTo(
				methodOn(UserController.class)
				.findById(userEntity.getId()))
				.withSelfRel());
		
		byte[] profilePicture = null;
		if(userEntity.getProfilePicture() != null && userEntity.getProfilePicture().length > 0) {
			profilePicture = 
					ImageHelper.decompressBytes(userEntity.getProfilePicture());
		} else {
			Resource imageResource = 
					resourceLoader.getResource("classpath:static/images/user-profile-default-image.jpg");
			try {
				profilePicture = Files.readAllBytes(Paths.get(imageResource.getURI()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		userModel.setId(userEntity.getId());
		userModel.setUsername(userEntity.getUsername());
		userModel.setEmail(userEntity.getEmail());
		userModel.setFirstName(userEntity.getFirstName());
		userModel.setMiddleName(userEntity.getMiddleName());
		userModel.setLastName(userEntity.getLastName());
		userModel.setSecondLastName(userEntity.getSecondLastName());
		userModel.setProfilePicture(profilePicture);
		userModel.setAddresses(this.addressesToModel(new ArrayList<>(userEntity.getAddresses()), userEntity));
		userModel.setPhones(this.PhonesToModel(new ArrayList<>(userEntity.getPhones())));
		
		return userModel;
	}
	
	@Override
	public CollectionModel<UserModel> toCollectionModel(
			Iterable<? extends UserEntity> users) {
		
		CollectionModel<UserModel> userModels =
				super.toCollectionModel(users);

		return userModels;
	}
	
	private List<AddressModel> addressesToModel(List<AddressEntity> addresses, UserEntity user) {
		return addresses.stream()
				.map(address -> {
					AddressModel addressModel = AddressModel.builder()
							.id(address.getId())
							.number(address.getNumber())
							.innerNumber(address.getInnerNumber())
							.street(address.getStreet())
							.zipPostcode(address.getZipPostcode())
							.city(address.getCity())
							.addressType(address.getAddressType())
							.build();
					
					if(user.getDefaultAddressId() != null && user.getDefaultAddressId() == address.getId()) {
						addressModel.setDefaultAddress(true);
					} else {
						addressModel.setDefaultAddress(false);
					}
					return addressModel;})
				.toList();
	}
	
	private List<PhoneModel> PhonesToModel(List<PhoneEntity> phones) {
		return phones.stream()
				.map(phone -> PhoneModel.builder()
						.id(phone.getId())
						.phoneNumber(phone.getPhoneNumber())
						.phoneType(phone.getPhoneType())
						.build())
				.toList();
	}

}
