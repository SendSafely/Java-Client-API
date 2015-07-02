package com.sendsafely.exceptions;

public class PackageInformationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public PackageInformationFailedException()
	{
		super();
		error = "unknown";
	}
	
	public PackageInformationFailedException(String err){
		super(err);
		error = err;
	}
	
	public PackageInformationFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
