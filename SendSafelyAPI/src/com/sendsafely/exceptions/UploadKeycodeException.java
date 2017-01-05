package com.sendsafely.exceptions;

public class UploadKeycodeException extends Exception
{
	private static final long serialVersionUID = 1L;

	String error;

	public UploadKeycodeException()
	{
		super();
		error = "unknown";
	}

	public UploadKeycodeException(String err){
		super(err);
		error = err;
	}

	public UploadKeycodeException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
