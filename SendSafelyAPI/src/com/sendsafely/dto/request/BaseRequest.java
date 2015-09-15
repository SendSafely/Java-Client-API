package com.sendsafely.dto.request;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class BaseRequest 
{
	private HTTPMethod method;
	private String path;
	private Map<String, Object> postParams;
	
	public BaseRequest()
	{
		this.postParams = new HashMap<String, Object>();
	}
	
	protected void initialize(HTTPMethod method, String path)
	{
		this.method = method;
		this.path = path;
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
		Gson gson = new Gson();
		return gson.toJson(postParams);
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
