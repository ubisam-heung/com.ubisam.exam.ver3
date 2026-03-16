package com.ubisam.exam.api.addresses;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ubisam.exam.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{


  // @RestResource(exported = false)
  List<Address> findByName(String name);
  
}
