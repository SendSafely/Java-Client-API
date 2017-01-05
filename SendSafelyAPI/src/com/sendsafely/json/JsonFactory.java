package com.sendsafely.json;

public class JsonFactory {

	public static JsonManager getDefaultManager()
	{
		DefaultJsonManager manager = new DefaultJsonManager();
		return manager;
	}
	
}
