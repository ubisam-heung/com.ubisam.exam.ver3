package com.ubisam.exam.api.addressGroups;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.AddressGroup;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class AddressGroupHandler {

    @HandleBeforeRead
    public void beforeSearch(AddressGroup addressGroup, Specification<AddressGroup> spec){
        JpaSpecificationBuilder<AddressGroup> query = JpaSpecificationBuilder.of(AddressGroup.class);
        // select * from example_address_group where description like '%키워드%' or name = '키워드'
        query.where()
            .and().eq("name", addressGroup.getKeyword())
            .or().like("description", "%" + addressGroup.getKeyword() + "%").build(spec);
    }
  
}
