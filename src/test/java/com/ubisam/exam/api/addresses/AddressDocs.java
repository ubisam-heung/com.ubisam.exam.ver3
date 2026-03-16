package com.ubisam.exam.api.addresses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Address;

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

  public List<Address> newEntities(int count) {
    return IntStream.rangeClosed(1, count)
      .mapToObj(i -> newEntity("길동" + i))
      .map(entityMap -> {
        Address address = new Address();
        address.setName((String) entityMap.get("name"));
        address.setPhone(String.valueOf(entityMap.get("phone")));
        address.setAddress((String) entityMap.get("address"));
        return address;
      })
      .toList();
  }
  
}
