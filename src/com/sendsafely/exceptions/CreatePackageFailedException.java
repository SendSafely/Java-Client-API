package com.sendsafely.exceptions;

public class CreatePackageFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public CreatePackageFailedException()
	{
		super();
		error = "unknown";
	}
	
	public CreatePackageFailedException(String err){
		super(err);
		error = err;
	}
	
	public CreatePackageFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
