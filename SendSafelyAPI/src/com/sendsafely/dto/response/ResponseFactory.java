package com.sendsafely.dto.response;

import com.google.gson.Gson;

public class ResponseFactory {

	@SuppressWarnings("unchecked")
	public static <T> T getInstanceFromString(String objectStr, T clazz)
	{
		Gson gson = new Gson();
		return (T) gson.fromJson(objectStr, clazz.getClass());
	}
	
}
