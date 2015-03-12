package com.sendsafely.exceptions;

public class UpdateRecipientFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public UpdateRecipientFailedException()
	{
		super();
		error = "unknown";
	}
	
	public UpdateRecipientFailedException(String err){
		super(err);
		error = err;
	}
	
	public UpdateRecipientFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
