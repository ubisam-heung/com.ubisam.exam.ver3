package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_address")
public class Address {

  @Id
  @GeneratedValue
  private Long id;

  private String phone;
  private String name;
  private String address;
  
}
