package com.sendsafely.exceptions;

public class UploadFileException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public UploadFileException()
	{
		super();
		error = "unknown";
	}
	
	public UploadFileException(String err){
		super(err);
		error = err;
	}
	
	public UploadFileException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
