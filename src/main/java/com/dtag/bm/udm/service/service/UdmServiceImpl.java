package com.dtag.bm.udm.service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dtag.bm.udm.service.dao.UdmRepository;
import com.dtag.bm.udm.service.model.UDMRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UdmServiceImpl implements UdmService {

	private static Logger logger = LoggerFactory.getLogger(UdmServiceImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	UdmRepository udmRepository;

	@Value("${udmManagementURL}")
	private String udmManagementURL;

	@Autowired
	private RestTemplate restTemplate;

	public String createUDM(String udmRequest) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("inside service IMPL");
		Logger logger = LoggerFactory.getLogger(this.getClass());
		ObjectMapper mapper = new ObjectMapper();
		UDMRequest request = new UDMRequest();
		request.setUdm(udmRequest);
		System.out.println(udmRequest);
		mongoTemplate.save(request);

		try {
			JsonNode node = mapper.readTree(udmRequest);
			String imsi = node.get("imsi").asText();
			String udmManagementURL1 = udmManagementURL + imsi + "/snssai";
			logger.info("udmManagementURL " + udmManagementURL1);

			// RestTemplate restTemplate = new RestTemplate();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<JsonNode> entity = new HttpEntity<JsonNode>(node, header);
			logger.info("udmManagement request " + entity);
			//ResponseEntity<JsonNode> response = restTemplate.postForEntity(udmManagementURL1, entity, JsonNode.class);
			//logger.info("udmManagement response  ", response);
		
		  } catch (Exception e) { // TODO Auto-generated catch block
		  e.printStackTrace(); 
		  throw new Exception(); 
		  }
		 
		return udmRequest;

	}

	@Override
	public void deleteByIMSI(String imsi, String sst, String sd, String plmn_id) {
		String udmManagementURL1 = udmManagementURL+imsi+"/snssais"+"/"+sst+"/"+ sd+"/"+plmn_id;
		logger.info("udmManagementURL " + udmManagementURL1);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			//restTemplate.delete(udmManagementURL1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public List<String> getByIMSI(String imsi) {
		List<String> response = null;
		String udmManagementURL1 = udmManagementURL+imsi+"/snssais";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			@SuppressWarnings("unused")
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			response =restTemplate.getForObject(udmManagementURL1,List.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		
	}


}
