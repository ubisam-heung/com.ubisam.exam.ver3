package com.ubisam.exam.api.addresses;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.is4xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressTests {
  
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AddressDocs docs;

  @Test
  void contextLoads() throws Exception {

    //JpaRepository
    mockMvc.perform(get("/api/addresses")).andExpect(is2xx());
    mockMvc.perform(post("/api/addresses/search")).andExpect(is4xx());
    mockMvc.perform(get("/api/addresses/search")).andExpect(is4xx());

    //Crud - C
    mockMvc
      .perform(post("/api/addresses")
      .content(docs::newEntity, "홍길동"))
      .andDo(print())
      .andExpect(is2xx())
      .andDo(result(docs::context , "entity1"))
    ;
    mockMvc
      .perform(post("/api/addresses")
      .content(docs::newEntity, "김길동"))
      .andDo(print())
      .andExpect(is2xx())
      .andDo(result(docs::context , "entity2"))
    ;
    mockMvc
      .perform(post("/api/addresses")
      .content(docs::newEntity, "박길동"))
      .andDo(print())
      .andExpect(is2xx())
      .andDo(result(docs::context , "entity3"))
    ;

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mockMvc
      .perform(get(url))
      .andDo(print())
      .andExpect(is2xx())
    ;
    mockMvc
      .perform(post(url))
      .andDo(print())
      .andExpect(is4xx())
    ;
    
    //Crud - U
    Map<String, Object> entity = docs.context("entity2");
    mockMvc
      .perform(put(url)
      .content(docs::updateEntity, entity, "홍111길동"))
      .andDo(print())
      .andExpect(is2xx())
    ;

    //Crud - D
    mockMvc
      .perform(delete(url))
      .andDo(print())
      .andExpect(is2xx())
    ;
  }
}
