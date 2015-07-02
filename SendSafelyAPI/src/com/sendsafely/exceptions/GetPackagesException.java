package com.sendsafely.exceptions;

public class GetPackagesException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public GetPackagesException()
	{
		super();
		error = "unknown";
	}
	
	public GetPackagesException(String err){
		super(err);
		error = err;
	}
	
	public GetPackagesException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
