package com.dtag.bm.udm.service.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtag.bm.udm.service.service.UdmServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/udmOrdering/v1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UdmController {
	private static Logger logger = LoggerFactory.getLogger(UdmController.class);
	@Autowired
	private UdmServiceImpl udmService;
	private final String className = this.getClass().getSimpleName();

	/**
	 * This will create new UdmEntry
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@ApiOperation(value = "create new UdmEntry", notes = "create new UdmEntry")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Created new UdmEntry"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/udmRequest")
	public ResponseEntity<?> createUDM(@RequestBody String udmRequest) throws Exception {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.info("udm request "+ udmRequest);
			//ObjectMapper mapper = new ObjectMapper();
			String response = udmService.createUDM(udmRequest);
			responseEntity = new ResponseEntity<>("Successfully Saved In DB", HttpStatus.CREATED);

			logger.info("udm api response completed", responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return responseEntity;

	}

	/**
	 * This will delete by plmn_id
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */

	@ApiOperation(value = "delete by plmn_id ", notes = "delete  by plmn_id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deleted by plmn_id"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("/udmRequest/imsi/{imsi}/snssais/sst/{sst}/sd/{sd}/plmn_id/{plmn_id}")
	public ResponseEntity<?> deletByIMSI(@PathVariable(value = "imsi") String imsi,
			@PathVariable(value="sst") String sst,
			@PathVariable(value="sd")String sd,
			@PathVariable(value = "plmn_id")String plmn_id)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			logger.info("udm call for delete API request param" +" imsi "+ imsi + " sst "+ sst + " sd "+sd + " plmn_id"+ plmn_id);
			udmService.deleteByIMSI(imsi,sst,sd,plmn_id);
			responseEntity = new ResponseEntity<>(" plmn_id "+ plmn_id +" for delete successfully", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}
	
	/**
	 * This will Get all snssais
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */

	@ApiOperation(value = "Get by IMSI number", notes = "Get by IMSI number")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get  all snssais"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("/udmRequest/imsi/{imsi}/snssais")
	public ResponseEntity<?> getByIMSI(@PathVariable(value = "imsi") String imsi)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			logger.info("udm call for get API request param" +" imsi "+ imsi);
			List<String> response =udmService.getByIMSI(imsi);
			if(response.isEmpty()) {
				responseEntity = new ResponseEntity<>("Not available", HttpStatus.NOT_FOUND);
			}else {
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}
}
