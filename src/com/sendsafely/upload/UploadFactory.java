package com.sendsafely.upload;

import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.credentials.CredentialsManager;

public class UploadFactory {
	
	public static UploadManager getManager(ConnectionManager connection, CredentialsManager manager)
	{
		return new DefaultUploadManager(connection, manager);
	}
	
}
