package com.sendsafely.exceptions;

public class DownloadFileException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public DownloadFileException()
	{
		super();
		error = "unknown";
	}
	
	public DownloadFileException(String err){
		super(err);
		error = err;
	}
	
	public DownloadFileException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
