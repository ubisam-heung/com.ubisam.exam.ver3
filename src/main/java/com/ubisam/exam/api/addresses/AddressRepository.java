package com.ubisam.exam.api.addresses;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubisam.exam.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
  
}
