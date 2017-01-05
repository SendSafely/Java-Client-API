package com.sendsafely.exceptions;

public class TwoFactorAuthException extends Exception {

	private static final long serialVersionUID = 1L;
	private String validationToken;
	String error;
	
	public TwoFactorAuthException(String err, String validationToken){
		super(err);
		error = err;
		this.validationToken = validationToken;
	}
	
	public String getValidationToken(){
		return validationToken;
	}
	public void setValidationToken(String validationToken){
		this.validationToken = validationToken;
	}
}
