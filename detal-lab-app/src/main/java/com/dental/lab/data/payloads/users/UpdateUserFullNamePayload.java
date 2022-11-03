package com.dental.lab.data.payloads.users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserFullNamePayload {
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String secondLastName;

}
