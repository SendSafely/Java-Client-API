package com.sendsafely.exceptions;

public class ContactGroupException extends Exception {
private static final long serialVersionUID = 1L;
	
	String error;
	
	public ContactGroupException()
	{
		super();
		error = "unknown";
	}
	
	public ContactGroupException(String err){
		super(err);
		error = err;
	}
	
	public ContactGroupException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
