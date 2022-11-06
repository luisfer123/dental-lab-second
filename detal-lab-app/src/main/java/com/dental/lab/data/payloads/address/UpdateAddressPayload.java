package com.dental.lab.data.payloads.address;

import com.dental.lab.data.enums.EAddressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressPayload {
		
	private int number;
	
	private String innerNumber;
	
	private String street;
	
	private int zipPostcode;
	
	private String city;
	
	private EAddressType addressType;

}
