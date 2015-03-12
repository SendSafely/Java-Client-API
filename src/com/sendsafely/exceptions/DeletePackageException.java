package com.sendsafely.exceptions;

public class DeletePackageException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public DeletePackageException()
	{
		super();
		error = "unknown";
	}
	
	public DeletePackageException(String err){
		super(err);
		error = err;
	}
	
	public DeletePackageException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
