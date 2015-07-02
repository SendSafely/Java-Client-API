package com.sendsafely.dto.response;

import java.util.List;

public class FinalizePackageResponse extends BaseResponse {
	
	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	
}
