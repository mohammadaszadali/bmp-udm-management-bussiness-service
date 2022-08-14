package com.dtag.bm.udm.service.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dtag.bm.udm.service.model.UDMRequest;

@Repository
public interface UdmRepository extends MongoRepository<UDMRequest, String> {
	
	

}
