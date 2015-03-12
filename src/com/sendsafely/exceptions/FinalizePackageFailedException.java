package com.sendsafely.exceptions;

import java.util.List;

public class FinalizePackageFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	private List<String> errors;
	
	String error;
	
	public FinalizePackageFailedException()
	{
		super();
		error = "unknown";
	}
	
	public FinalizePackageFailedException(String err){
		super(err);
		error = err;
	}
	
	public FinalizePackageFailedException(String err, List<String> errors){
		super(err);
		error = err;
		this.errors = errors;
	}
	
	public FinalizePackageFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
	
	public List<String> getErrors()
	{
		return errors;
	}
	
}
