package com.dtag.bm.udm.service.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UDM_MANAGEMENT")
public class UDMRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2512178755001293933L;
	private String udm;

	public String getUdm() {
		return udm;
	}

	public void setUdm(String udm) {
		this.udm = udm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
