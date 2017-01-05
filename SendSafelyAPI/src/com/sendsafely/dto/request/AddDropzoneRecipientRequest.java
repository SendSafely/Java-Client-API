package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class AddDropzoneRecipientRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/user/dropzone-recipients/";
	
	public AddDropzoneRecipientRequest(JsonManager jsonManager, String email){
		initialize(jsonManager, method, path);
		
		super.setPostParam("userEmail", email);
	}
	
	public AddDropzoneRecipientRequest(){
		
	}
	
}
