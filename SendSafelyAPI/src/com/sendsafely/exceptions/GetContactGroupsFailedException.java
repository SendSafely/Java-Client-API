package com.sendsafely.exceptions;

public class GetContactGroupsFailedException extends Exception {
private static final long serialVersionUID = 1L;
	
	String error;
	
	public GetContactGroupsFailedException()
	{
		super();
		error = "unknown";
	}
	
	public GetContactGroupsFailedException(String err){
		super(err);
		error = err;
	}
	
	public GetContactGroupsFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
