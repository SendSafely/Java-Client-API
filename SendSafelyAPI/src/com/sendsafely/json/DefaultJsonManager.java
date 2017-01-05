package com.sendsafely.json;

import com.google.gson.Gson;
import com.sendsafely.exceptions.SendFailedException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class DefaultJsonManager implements JsonManager {

	public DefaultJsonManager() {
	}


    @Override
    public String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    @Override
    public <T> T fromJson(String json, T returnObject) {
        Gson gson = new Gson();
        return (T) gson.fromJson(json, returnObject.getClass());
    }


}
