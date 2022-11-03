package com.dental.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dental.lab.data.Entities.PhoneEntity;

public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {

}
