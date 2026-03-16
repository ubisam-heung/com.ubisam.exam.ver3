package com.ubisam.exam.api.addressGroups;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.AddressGroup;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressGroupsTests {
  
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AddressGroupsDocs docs;

  @Autowired
  private AddressGroupRepository addressGroupRepository;

  @Test
  void contextLoads() throws Exception {

    //Crud - C
    mockMvc
      .perform(post("/api/addressGroups")
      .content(docs::newEntity, "홍길동"))
      .andExpect(is2xx())
      .andDo(result(docs::context , "entity1"))
    ;

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mockMvc
      .perform(get(url))
      .andExpect(is4xx())
    ;
    mockMvc
      .perform(post(url))
      .andExpect(is2xx())
      .andExpect(isJson("$.name", "홍길동"))
    ;

    //Crud - U
    Map<String, Object> entity = docs.context("entity1");
    mockMvc
      .perform(put(url)
      .content(docs::updateEntity, entity, "박박길동"))
      .andExpect(is2xx())
      .andExpect(isJson("$.name", "박박길동"))
    ;

    //Crud - D
    mockMvc
      .perform(delete(url))
      .andExpect(is2xx());
    
  }
  //Search 추가 (메소드를 추가로) 30개
  //리포지토리(saveAll)로 데이터 주입 후 검색 테스트
  //where 조건 = 이름 or 전화번호 or 주소에 '검색어'가 포함된 엔티티 검색
  //select * from address where name = '홍길동' or phone like '010-1234-5678'
  //페이지네이션(sort)
  //orderby
  //isJson 을 통해 검색 결과 검증
  @Test
  void contextLoads2() throws Exception {

    // 30개의 Address
    List<AddressGroup> addressGroupList = docs.newEntities(30);
    addressGroupRepository.saveAll(addressGroupList);

    // 전체 조회 후 개수 검증
    mockMvc.perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, ""))
      .andExpect(is2xx())
      .andExpect(isJson("$.page.totalElements", 30)); 

    //where 조건 검증
    mockMvc
      .perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, "연구소4"))
      .andExpect(is2xx())
      .andExpect(isJson("$._embedded.addressGroups[0].name", "연구소4"));
    ;

    //페이지네이션 검증 (5개씩 6페이지)
    mockMvc
      .perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, "")
      .param("page", "5")
      .param("size", "5"))
      .andExpect(is2xx())
      .andExpect(isJson("$.page.totalElements", 30))
      .andExpect(isJson("$.page.totalPages", 6))
      .andExpect(isJson("$.page.number", 5))
      .andExpect(isJson("$.page.size", 5));

    //정렬 검증 (이름 내림차순) - 10개씩 2페이지 이면서 1번째 페이지 제일 위가 '연구소9'인지 검증
    mockMvc
      .perform(post("/api/addressGroups/search")
      .content(docs::setKeyword, "")
      .param("sort", "name,desc")
      .param("size", "10")
      .param("page", "0"))
      .andExpect(is2xx())
      .andExpect(isJson("$._embedded.addressGroups[0].name", "연구소9"));
  }
}
