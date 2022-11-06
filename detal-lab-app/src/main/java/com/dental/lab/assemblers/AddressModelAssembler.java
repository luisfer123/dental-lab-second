package com.dental.lab.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.dental.lab.controllers.AddressController;
import com.dental.lab.data.Entities.AddressEntity;
import com.dental.lab.data.models.AddressModel;

@Component
public class AddressModelAssembler extends RepresentationModelAssemblerSupport<AddressEntity, AddressModel> {

	public AddressModelAssembler(Class<?> controllerClass, Class<AddressModel> resourceType) {
		super(controllerClass, resourceType);
	}
	
	public AddressModelAssembler() {
		this(AddressController.class, AddressModel.class);
	}

	@Override
	public AddressModel toModel(AddressEntity address) {
		
		AddressModel addressModel = instantiateModel(address);
		
		addressModel.add(linkTo(
				methodOn(AddressController.class)
				.findById(address.getId()))
				.withSelfRel());
		
		addressModel.setId(address.getId());
		addressModel.setNumber(address.getNumber());
		addressModel.setInnerNumber(address.getInnerNumber());
		addressModel.setStreet(address.getStreet());
		addressModel.setCity(address.getCity());
		addressModel.setAddressType(address.getAddressType());
		addressModel.setZipPostcode(address.getZipPostcode());
		
		boolean isDefaultAddress = address.getUser() != null ?
				address.getId() == address.getUser().getDefaultAddressId() : false;
		
		addressModel.setDefaultAddress(isDefaultAddress);
		
		return addressModel;
	}

	@Override
	public CollectionModel<AddressModel> toCollectionModel(
			Iterable<? extends AddressEntity> addresses) {
		
		CollectionModel<AddressModel> addressesModel = 
				super.toCollectionModel(addresses);
		
		addressesModel.add(linkTo(
				methodOn(AddressController.class)
				.findAllForLoggedInUser())
				.withSelfRel());
		
		return addressesModel;
	}

}
