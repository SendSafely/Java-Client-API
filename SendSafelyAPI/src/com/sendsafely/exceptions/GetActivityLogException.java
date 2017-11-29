package com.sendsafely.exceptions;

public class GetActivityLogException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public GetActivityLogException()
	{
		super();
		error = "unknown";
	}
	
	public GetActivityLogException(String err){
		super(err);
		error = err;
	}
	
	public GetActivityLogException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
