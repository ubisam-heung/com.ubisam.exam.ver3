package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_address_group")
public class AddressGroup {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  
}
