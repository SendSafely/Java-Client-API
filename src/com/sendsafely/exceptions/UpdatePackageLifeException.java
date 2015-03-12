package com.sendsafely.exceptions;

public class UpdatePackageLifeException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public UpdatePackageLifeException()
	{
		super();
		error = "unknown";
	}
	
	public UpdatePackageLifeException(String err){
		super(err);
		error = err;
	}
	
	public UpdatePackageLifeException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
