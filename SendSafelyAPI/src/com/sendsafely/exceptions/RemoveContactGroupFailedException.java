package com.sendsafely.exceptions;

public class RemoveContactGroupFailedException extends Exception {
private static final long serialVersionUID = 1L;
	
	String error;
	
	public RemoveContactGroupFailedException()
	{
		super();
		error = "unknown";
	}
	
	public RemoveContactGroupFailedException(String err){
		super(err);
		error = err;
	}
	
	public RemoveContactGroupFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
