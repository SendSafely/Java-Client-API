package com.sendsafely.exceptions;

public class GetDownloadUrlsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public GetDownloadUrlsException(Exception e) {
		super(e);
		error = e.getMessage();
	}

	public GetDownloadUrlsException(String err){
		super(err);
		error = err;
	}

}
