package com.sendsafely.dto;

public class EnterpriseInfo {

	private String host;
	private String systemName;
	
	/**
	 * @description Returns the host name for the organization.
	 * @return The host name for the organization.
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/** 
	 * @description Returns the system name for the organization.
	 * @return The system name for the organization.
	 */
	public String getSystemName() {
		return systemName;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param systemName
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
}
