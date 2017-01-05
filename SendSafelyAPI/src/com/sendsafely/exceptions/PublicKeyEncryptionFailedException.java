package com.sendsafely.exceptions;

public class PublicKeyEncryptionFailedException extends Exception
{
	private static final long serialVersionUID = 1L;

	String error;

	public PublicKeyEncryptionFailedException()
	{
		super();
		error = "unknown";
	}

	public PublicKeyEncryptionFailedException(String err){
		super(err);
		error = err;
	}

	public PublicKeyEncryptionFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
