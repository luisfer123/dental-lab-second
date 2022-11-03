package com.dental.lab.data.models;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.dental.lab.data.enums.EPhoneType;
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
@JsonRootName(value = "phone")
@Relation(collectionRelation = "phones", itemRelation = "phone")
@JsonInclude(Include.NON_NULL)
public class PhoneModel extends RepresentationModel<PhoneModel> {
	
	private Long id;
	
	private int phoneNumber;
	
	private EPhoneType phoneType;

}
