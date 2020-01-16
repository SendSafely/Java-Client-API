package com.sendsafely.exceptions;

import java.util.List;

public class NotifyPackageRecipientsException extends Exception
{
	private static final long serialVersionUID = 1L;
	private List<String> errors;
	
	String error;
	
	public NotifyPackageRecipientsException()
	{
		super();
		error = "unknown";
	}
	
	public NotifyPackageRecipientsException(String err){
		super(err);
		error = err;
	}
	
	public NotifyPackageRecipientsException(String err, List<String> errors){
		super(err);
		error = err;
		this.errors = errors;
	}
	
	public NotifyPackageRecipientsException(Exception e){
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
