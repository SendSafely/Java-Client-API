package com.sendsafely.dto.response;

import com.sendsafely.enums.APIResponse;

public class BaseResponse {

	private APIResponse response;
	private String message;
	
	public APIResponse getResponse() {
		return response;
	}
	public void setResponse(APIResponse response) {
		this.response = response;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
