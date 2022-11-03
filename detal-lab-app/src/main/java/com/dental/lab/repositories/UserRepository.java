package com.dental.lab.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dental.lab.data.Entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	@Query(value = "select u from UserEntity u join fetch u.authorities where u.username = :username",
			countQuery = "select count(u) from UserEntity u join fetch u.authorities where u.username = :username")
	Optional<UserEntity> findByUsernameWithAuthorities(@Param("username") String username);
	
	@EntityGraph(value = "UserEntity.AddressesPhones", type = EntityGraphType.FETCH)
	@Query(value = "select u from UserEntity u where u.id = :userId")
	Optional<UserEntity> findByIdWithPhonesAndAddresses(@Param("userId") Long userId);
	
	Optional<UserEntity> findByUsername(String username);
	
	Optional<UserEntity> findById(Long id);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
