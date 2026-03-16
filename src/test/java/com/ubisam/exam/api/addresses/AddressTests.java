package com.ubisam.exam.api.addresses;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.is4xx;
import static io.u2ware.common.docs.MockMvcRestDocs.isJson;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Address;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressTests {
  
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AddressDocs docs;

  @Autowired
  private AddressRepository addressRepository;
  
  @Test
  void contextLoads() throws Exception {

    //Crud - C
    mockMvc
      .perform(post("/api/addresses")
      .content(docs::newEntity, "홍길동"))
      .andDo(print())
      .andExpect(is2xx())
      .andDo(result(docs::context , "entity1"))
    ;

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mockMvc
      .perform(get(url))
      .andDo(print())
      .andExpect(is2xx())
      .andExpect(isJson("$.name", "홍길동"))
    ;
    mockMvc
      .perform(post(url))
      .andDo(print())
      .andExpect(is4xx())
    ;

    //Crud - U
    Map<String, Object> entity = docs.context("entity1");
    mockMvc
      .perform(put(url)
      .content(docs::updateEntity, entity, "홍111길동"))
      .andDo(print())
      .andExpect(is2xx())
      .andExpect(isJson("$.name", "홍111길동"))
    ;

    //Crud - D
    mockMvc
      .perform(delete(url))
      .andDo(print())
      .andExpect(is2xx())
    ;
  }

  @Test
  void contextLoads2() throws Exception {
    // 30개의 Address
    List<Address> addressList = docs.newEntities(30);
    addressRepository.saveAll(addressList);

    // 전체 조회 후 개수 검증
    mockMvc.perform(get("/api/addresses"))
      .andDo(print())
      .andExpect(is2xx())
      .andExpect(isJson("$.page.totalElements", 30)); 

    //링크 목록 확인
    mockMvc.perform(get("/api/addresses/search"))
    .andDo(print());
    
    // 검색 테스트 - 이름이 '길동4'인 엔티티 검증
    mockMvc.perform(get("/api/addresses/search/findByName")
      .param("name", "길동4"))
      .andDo(print())
      .andExpect(is2xx())
      .andExpect(isJson("$._embedded.addresses[0].name", "길동4"));

    //페이지네이션 검증 (5개씩 6페이지)
    mockMvc
      .perform(get("/api/addresses")
      .param("page", "5")
      .param("size", "5"))
      .andExpect(is2xx())
      .andExpect(isJson("$.page.totalElements", 30))
      .andExpect(isJson("$.page.totalPages", 6))
      .andExpect(isJson("$.page.number", 5))
      .andExpect(isJson("$.page.size", 5));

    //정렬 검증 (이름 내림차순) - 10개씩 2페이지 이면서 1번째 페이지 제일 위가 '길동9'인지 검증
    mockMvc
      .perform(get("/api/addresses")
      .param("sort", "name,desc")
      .param("size", "10")
      .param("page", "0"))
      .andExpect(is2xx())
      .andExpect(isJson("$._embedded.addresses[0].name", "길동9"));
      
  }
}
