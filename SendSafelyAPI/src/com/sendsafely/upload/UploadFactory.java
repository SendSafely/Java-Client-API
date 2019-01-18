package com.sendsafely.upload;

import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.credentials.CredentialsManager;
import com.sendsafely.json.JsonFactory;
import com.sendsafely.json.JsonManager;

public class UploadFactory {
	
	public static UploadManager getManager(ConnectionManager connection, CredentialsManager manager)
	{
		return getManager(connection, manager, JsonFactory.getDefaultManager());
	}

    public static UploadManager getManager(ConnectionManager connection, CredentialsManager manager, JsonManager jsonManager)
    {
        return new DefaultUploadManager(connection, manager, jsonManager);
    }
    
    public static UploadManager getManagerS3(ConnectionManager connection)
    {
        return new S3UploadManager(connection, JsonFactory.getDefaultManager());
    }
	
}
