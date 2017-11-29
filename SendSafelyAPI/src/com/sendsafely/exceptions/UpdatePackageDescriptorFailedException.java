package com.sendsafely.exceptions;

public class UpdatePackageDescriptorFailedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public UpdatePackageDescriptorFailedException()
	{
		super();
		error = "unknown";
	}
	
	public UpdatePackageDescriptorFailedException(String err){
		super(err);
		error = err;
	}
	
	public UpdatePackageDescriptorFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
