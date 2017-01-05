package com.sendsafely.exceptions;

public class DropzoneRecipientFailedException extends RecipientFailedException {
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public DropzoneRecipientFailedException(){
		super();
		error="unknown";
	}
	
	public DropzoneRecipientFailedException(String err){
		super(err);
		error =err;
	}
	
	public DropzoneRecipientFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError(){
		return error; 
	}
}
