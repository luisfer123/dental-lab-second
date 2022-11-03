package com.dental.lab.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dental.lab.data.Entities.AddressEntity;
import com.dental.lab.data.Entities.AuthorityEntity;
import com.dental.lab.data.Entities.PhoneEntity;
import com.dental.lab.data.Entities.UserEntity;
import com.dental.lab.data.enums.EAddressType;
import com.dental.lab.data.enums.EPhoneType;
import com.dental.lab.data.enums.ERole;
import com.dental.lab.repositories.AddressRepository;
import com.dental.lab.repositories.AuthorityRepository;
import com.dental.lab.repositories.PhoneRepository;
import com.dental.lab.repositories.UserRepository;

@Service
public class DataIntializationServiceImpl {
	
	@Autowired
	private AuthorityRepository authRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired private PhoneRepository phoneRepo;
	@Autowired private PasswordEncoder encoder;
	
	@Transactional
	@EventListener(classes = ApplicationReadyEvent.class)
	public void onInit() {
		addAuthorities();
		addAddressAndPhones();
	}
	
	@Transactional
	public void addAuthorities() {
		if(authRepo.count() == 0) {
			AuthorityEntity roleUser = new AuthorityEntity(ERole.ROLE_USER);
			AuthorityEntity roleClient = new AuthorityEntity(ERole.ROLE_CLIENT);
			AuthorityEntity roleTech = new AuthorityEntity(ERole.ROLE_TECHNICIAN);
			AuthorityEntity roleDentist = new AuthorityEntity(ERole.ROLE_DENTIST);
			AuthorityEntity roleAdmin = new AuthorityEntity(ERole.ROLE_ADMIN);
			
			roleUser = authRepo.save(roleUser);
			roleClient = authRepo.save(roleClient);
			roleTech = authRepo.save(roleTech);
			roleDentist = authRepo.save(roleDentist);
			roleAdmin = authRepo.save(roleAdmin);
			
			UserEntity user = UserEntity.builder()
					.username("user")
					.email("user@mail.com")
					.password(encoder.encode("password"))
					.firstName("username")
					.middleName("usermiddlename")
					.lastName("userlastname")
					.secondLastName("userslastname")
					.build();
			
			user.addAuthority(roleUser);			
			user = userRepo.save(user);
			
			UserEntity client = UserEntity.builder()
					.username("client")
					.email("client@mail.com")
					.password(encoder.encode("password"))
					.firstName("clientname")
					.middleName("clientmiddlename")
					.lastName("clientlastname")
					.secondLastName("clientslastname")
					.build();
			
			client.addAuthority(roleUser);
			client.addAuthority(roleClient);
			client = userRepo.save(client);
			
			UserEntity tech = UserEntity.builder()
					.username("tech")
					.email("tech@mail.com")
					.password(encoder.encode("password"))
					.firstName("techname")
					.middleName("techmiddlename")
					.lastName("techlastname")
					.secondLastName("techslastname")
					.build();
			
			tech.addAuthority(roleUser);
			tech.addAuthority(roleTech);
			tech = userRepo.save(tech);
			
			UserEntity dentist = UserEntity.builder()
					.username("dentist")
					.email("dentist@mail.com")
					.password(encoder.encode("password"))
					.firstName("dentistname")
					.middleName("dentistmiddlename")
					.lastName("dentistlastname")
					.secondLastName("dentistslastname")
					.build();
			
			dentist.addAuthority(roleUser);
			dentist.addAuthority(roleClient);
			dentist.addAuthority(roleDentist);
			dentist = userRepo.save(dentist);
			
			UserEntity admin = UserEntity.builder()
					.username("admin")
					.email("admin@mail.com")
					.password(encoder.encode("password"))
					.firstName("adminname")
					.middleName("adminmiddlename")
					.lastName("adminlastname")
					.secondLastName("adminslastname")
					.build();
			
			admin.addAuthority(roleUser);
			admin.addAuthority(roleClient);
			admin.addAuthority(roleTech);
			admin.addAuthority(roleDentist);
			admin.addAuthority(roleAdmin);
			admin = userRepo.save(admin);
		}
	}
	
	public void addAddressAndPhones() {
		if(addressRepo.count() == 0 && phoneRepo.count() == 0) {
			UserEntity admin = userRepo.findByUsername("admin")
					.get();
			
			AddressEntity adminAddress = AddressEntity.builder()
					.number(3)
					.innerNumber("2a")
					.street("admin street")
					.city("Morelia")
					.addressType(EAddressType.DENTIST_OFFICE)
					.zipPostcode(28118)
					.build();
			
			admin.addAddress(adminAddress);
			addressRepo.save(adminAddress);
			
			AddressEntity adminAddress2 = AddressEntity.builder()
					.number(33)
					.innerNumber("2b")
					.street("admin street")
					.city("Morelia")
					.addressType(EAddressType.OTHER)
					.zipPostcode(28118)
					.build();
			
			admin.addAddress(adminAddress2);
			addressRepo.save(adminAddress2);
			
			AddressEntity adminAddress3 = AddressEntity.builder()
					.number(31)
					.innerNumber("12a")
					.street("admin street")
					.city("Morelia")
					.addressType(EAddressType.HOME)
					.zipPostcode(28118)
					.build();
			
			admin.addAddress(adminAddress3);
			addressRepo.save(adminAddress3);
			
			PhoneEntity adminPhone = PhoneEntity.builder()
					.phoneNumber(3210934)
					.phoneType(EPhoneType.CELLPHONE)
					.build();
			admin.addPhone(adminPhone);
			phoneRepo.save(adminPhone);
			
			PhoneEntity adminPhone2 = PhoneEntity.builder()
					.phoneNumber(3213435)
					.phoneType(EPhoneType.OFFICE)
					.build();
			admin.addPhone(adminPhone2);
			phoneRepo.save(adminPhone2);
			
			admin.setDefaultAddressId(adminAddress.getId());
			userRepo.save(admin);
			
			// TODO: add addresses and phones for other users
		}
	}

}
