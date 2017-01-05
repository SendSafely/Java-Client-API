package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetDropzoneRecipientRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/user/dropzone-recipients/";
	
	public GetDropzoneRecipientRequest(JsonManager jsonManager) {

		initialize(jsonManager, method, path);
	}
	
	public GetDropzoneRecipientRequest(){
		
	}

}
