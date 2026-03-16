package com.ubisam.exam.api.addresses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class AddressDocs extends MockMvcRestDocs{

  public Map<String, Object> newEntity(String name){
    Map<String, Object> address = new HashMap<>();
    address.put("name", name);
    address.put("phone", super.randomInt());
    address.put("address", super.randomText("address"));
    return address;
  };

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }
  
}
