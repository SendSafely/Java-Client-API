package com.sendsafely.exceptions;

public class PublicKeysFailedException extends Exception
{
	private static final long serialVersionUID = 1L;

	String error;

	public PublicKeysFailedException()
	{
		super();
		error = "unknown";
	}

	public PublicKeysFailedException(String err){
		super(err);
		error = err;
	}

	public PublicKeysFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
