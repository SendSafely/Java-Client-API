package com.sendsafely.credentials;

import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.exceptions.CredentialsException;

public class DropzoneCredentials implements CredentialsManager 
{
	private final String apiPath = "/drop-zone/v2.0";
	
	private final String API_KEY_HEADER = "ss-api-key";
	
	private String apiKey;
	
	public DropzoneCredentials(String apiKey)
	{
		this.apiKey = apiKey;
	}

	@Override
	public String getAPIPath()
	{
		return apiPath;
	}
	
	@Override
	public void addCredentials(ConnectionManager conn, String data) throws CredentialsException 
	{	
		conn.addHeader(API_KEY_HEADER, apiKey);
	}
}
