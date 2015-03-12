package com.sendsafely.credentials;

public class CredentialsFactory {

	public static CredentialsManager getDefaultCredentials(String apiKey, String apiSecret)
	{
		return new DefaultCredentials(apiKey, apiSecret);
	}
	
}
