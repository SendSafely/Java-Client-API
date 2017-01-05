package com.sendsafely.exceptions;

public class GetKeycodeFailedException extends Exception
{
	private static final long serialVersionUID = 1L;

	String error;

	public GetKeycodeFailedException()
	{
		super();
		error = "unknown";
	}

	public GetKeycodeFailedException(String err){
		super(err);
		error = err;
	}

	public GetKeycodeFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
