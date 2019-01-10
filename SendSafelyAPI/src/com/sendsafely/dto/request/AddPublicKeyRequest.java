package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class AddPublicKeyRequest extends BaseRequest
{

	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/public-key/";

	public AddPublicKeyRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}

	public void setPublicKey(String publicKey)
	{
		super.setPostParam("publicKey", publicKey);
	}
	
	public void setDescription(String description){
		super.setPostParam("description", description);
	}
	
}
