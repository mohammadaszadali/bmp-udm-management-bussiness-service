package com.dtag.bm.udm.service.service;

import java.util.List;

public interface UdmService {

	public String createUDM(String udmRequest) throws Exception;

	public void deleteByIMSI(String imsi, String sst, String sd, String plmn_id);
	
	public List<String> getByIMSI(String imsi);

}
