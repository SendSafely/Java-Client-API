package com.sendsafely.exceptions;

public class FileOperationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public FileOperationFailedException()
	{
		super();
		error = "unknown";
	}
	
	public FileOperationFailedException(String err){
		super(err);
		error = err;
	}
	
	public FileOperationFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
