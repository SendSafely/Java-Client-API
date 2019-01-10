package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class RevokePublicKeyRequest extends BaseRequest
{

	private HTTPMethod method = HTTPMethod.DELETE;
	private String path = "/public-key/" + GetParam.PUBLIC_KEY_ID + "/";

	public RevokePublicKeyRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}

	public void setPublicKey(String publicKey)
	{
		super.setGetParam(GetParam.PUBLIC_KEY_ID, publicKey);
	}
	
}
