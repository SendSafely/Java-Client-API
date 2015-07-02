package com.sendsafely.credentials;

import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.exceptions.CredentialsException;

public interface CredentialsManager {

	public String getAPIPath();
	public void addCredentials(ConnectionManager conn, String data) throws CredentialsException;
	
}
