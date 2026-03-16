package com.ubisam.exam.api.addressGroups;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class AddressGroupsDocs extends MockMvcRestDocs{

  public Map<String, Object> newEntity(String name){
    Map<String, Object> address = new HashMap<>();
    address.put("name", name);
    return address;
  };

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }
  
}
