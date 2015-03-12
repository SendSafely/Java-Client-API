package com.sendsafely.connection;

public class ConnectionFactory {

	public static ConnectionManager getDefaultManager(String host)
	{
		DefaultConnectionManager manager = new DefaultConnectionManager(host);
		return manager;
	}
	
}
