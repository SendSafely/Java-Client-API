package com.sendsafely.exceptions;

public class RemoveContactGroupAsRecipientFailedException extends Exception {
private static final long serialVersionUID = 1L;
	
	String error;
	
	public RemoveContactGroupAsRecipientFailedException()
	{
		super();
		error = "unknown";
	}
	
	public RemoveContactGroupAsRecipientFailedException(String err){
		super(err);
		error = err;
	}
	
	public RemoveContactGroupAsRecipientFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
