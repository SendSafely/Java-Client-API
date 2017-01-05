package com.sendsafely.exceptions;

public class PublicKeyDecryptionFailedException extends Exception
{
	private static final long serialVersionUID = 1L;

	String error;

	public PublicKeyDecryptionFailedException()
	{
		super();
		error = "unknown";
	}

	public PublicKeyDecryptionFailedException(String err){
		super(err);
		error = err;
	}

	public PublicKeyDecryptionFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
