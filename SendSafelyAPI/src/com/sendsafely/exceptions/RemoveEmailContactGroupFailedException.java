package com.sendsafely.exceptions;

public class RemoveEmailContactGroupFailedException extends Exception {
private static final long serialVersionUID = 1L;
	
	String error;
	
	public RemoveEmailContactGroupFailedException()
	{
		super();
		error = "unknown";
	}
	
	public RemoveEmailContactGroupFailedException(String err){
		super(err);
		error = err;
	}
	
	public RemoveEmailContactGroupFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
