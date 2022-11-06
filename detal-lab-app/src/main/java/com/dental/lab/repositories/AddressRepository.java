package com.dental.lab.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dental.lab.data.Entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
	
	@Query(value = "select a from AddressEntity a join fetch a.user u where a.id = :addressId")
	Optional<AddressEntity> findByIdWithUser(@Param("addressId") Long addressId);
	
	Optional<AddressEntity> findById(Long addressId);
	
	@Query(value = "select a from AddressEntity a join fetch a.user u where a.user.id = :userId")
	Set<AddressEntity> findByUserIdWithUser(@Param("userId") Long userId);

}
