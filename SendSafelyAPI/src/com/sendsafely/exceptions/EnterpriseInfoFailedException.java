package com.sendsafely.exceptions;

public class EnterpriseInfoFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public EnterpriseInfoFailedException()
	{
		super();
		error = "unknown";
	}
	
	public EnterpriseInfoFailedException(String err){
		super(err);
		error = err;
	}
	
	public EnterpriseInfoFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
