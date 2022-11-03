package com.dental.lab.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dental.lab.data.Entities.AuthorityEntity;
import com.dental.lab.data.enums.ERole;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
	
	Optional<AuthorityEntity> findByRole(ERole name);

}
