package com.sendsafely.dto.request;

import java.util.HashMap;
import java.util.Map;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class BaseRequest 
{
	private HTTPMethod method;
	private String path;
	private Map<String, Object> postParams;
    private JsonManager jsonManager;
	
	public BaseRequest()
	{
		this.postParams = new HashMap<String, Object>();
	}
	
	protected void initialize(JsonManager jsonManager, HTTPMethod method, String path)
	{
		this.method = method;
		this.path = path;
        this.jsonManager = jsonManager;
	}
	
	protected void setGetParam(GetParam key, Object value)
	{
		this.path = this.path.replaceAll(key.toString(), String.valueOf(value));
	}
	
	protected void setPostParam(String key, Object value)
	{
		postParams.put(key, value);
	}
	
	public String getPostBody()
	{
        return jsonManager.toJson(postParams);
	}
	
	public boolean hasPostBody()
	{
		return postParams.size() > 0;
	}
	
	public HTTPMethod getMethod()
	{
		return this.method;
	}
	
	public String getPath()
	{
		return this.path;
	}
}
