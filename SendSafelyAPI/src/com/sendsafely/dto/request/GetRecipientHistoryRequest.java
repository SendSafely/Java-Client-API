package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetRecipientHistoryRequest extends BaseRequest{

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/recipient/history/" + GetParam.RECIPIENT_EMAIL + "/";
	
	public GetRecipientHistoryRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setRecipientEmail(String recipientEmail)
	{
		super.setGetParam(GetParam.RECIPIENT_EMAIL, recipientEmail);
	}
	
}
