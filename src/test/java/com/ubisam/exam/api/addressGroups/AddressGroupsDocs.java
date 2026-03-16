package com.ubisam.exam.api.addressGroups;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.AddressGroup;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class AddressGroupsDocs extends MockMvcRestDocs{

  public Map<String, Object> newEntity(String name){
    Map<String, Object> address = new HashMap<>();
    address.put("name", name);
    address.put("description", super.randomText("description"));
    return address;
  };

  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

  public List<AddressGroup> newEntities(int count) {
  return IntStream.rangeClosed(1, count)
    .mapToObj(i -> newEntity("연구소" + i))
    .map(entityMap -> {
      AddressGroup addressGroup = new AddressGroup();
      addressGroup.setName((String) entityMap.get("name"));
      addressGroup.setDescription((String) entityMap.get("description"));
      return addressGroup;
    })
    .toList();
  }

  public Map<String, Object> setKeyword(String keyword){
    Map<String, Object> addressGroup = new HashMap<>();
    addressGroup.put("keyword", keyword);
    return addressGroup;
  }
  
}
