package com.sendsafely.exceptions;

public class MessageException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public MessageException()
	{
		super();
		error = "unknown";
	}
	
	public MessageException(String err){
		super(err);
		error = err;
	}
	
	public MessageException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
