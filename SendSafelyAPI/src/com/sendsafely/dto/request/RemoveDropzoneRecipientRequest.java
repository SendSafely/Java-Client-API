package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class RemoveDropzoneRecipientRequest extends BaseRequest {
	private HTTPMethod method = HTTPMethod.DELETE;
	private String path = "/user/dropzone-recipients/";
	
	public RemoveDropzoneRecipientRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setEmail(String email)
	{
		super.setPostParam("userEmail", email);
	}
	
}
