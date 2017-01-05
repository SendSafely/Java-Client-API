package com.sendsafely.dto.response;

import com.sendsafely.json.JsonManager;

public class ResponseFactory {

	@SuppressWarnings("unchecked")
	public static <T> T getInstanceFromString(String objectStr, T clazz, JsonManager jsonManager)
	{
        return (T) jsonManager.fromJson(objectStr, clazz);
	}
	
}
