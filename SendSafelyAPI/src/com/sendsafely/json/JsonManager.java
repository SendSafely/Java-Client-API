package com.sendsafely.json;

public interface JsonManager {

    public String toJson(Object obj);
    public <T> T fromJson(String json, T returnObject);
	
}
