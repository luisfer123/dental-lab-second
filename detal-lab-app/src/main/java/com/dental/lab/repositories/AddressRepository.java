package com.dental.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dental.lab.data.Entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
