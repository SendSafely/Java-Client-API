package com.sendsafely.exceptions;

public class ApproverRequiredException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public ApproverRequiredException()
	{
		super();
		error = "unknown";
	}
	
	public ApproverRequiredException(String err){
		super(err);
		error = err;
	}
	
	public ApproverRequiredException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
