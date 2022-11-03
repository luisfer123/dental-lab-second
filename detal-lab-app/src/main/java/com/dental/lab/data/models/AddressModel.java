package com.dental.lab.data.models;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.dental.lab.data.enums.EAddressType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "address")
@Relation(collectionRelation = "addresses", itemRelation = "address")
@JsonInclude(Include.NON_NULL)
public class AddressModel extends RepresentationModel<AddressModel> {
	
	private Long id;
	
	private int number;
	
	private String innerNumber;
	
	private String street;
	
	private int zipPostcode;
	
	private String city;
	
	private EAddressType addressType;
	
	/**
	 * This field does not belongs to {@code AddressEntity}, and it is compute
	 * by the {@code AddressModelAssembler} using {@code defaultAddressId} field
	 * within {@code UserEntity}.
	 */
	private boolean isDefaultAddress;

}
