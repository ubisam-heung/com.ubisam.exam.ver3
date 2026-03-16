package com.ubisam.exam.api.addressGroups;

import com.ubisam.exam.domain.AddressGroup;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;
import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;


public interface AddressGroupRepository extends RestfulJpaRepository<AddressGroup, Long>{
  
  // @RestResource(exported = false)
  List<AddressGroup> findByName(String name);
}
