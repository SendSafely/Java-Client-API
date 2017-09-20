package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class CreateContactGroupRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/group/";
	
	public CreateContactGroupRequest(JsonManager jsonManager, String name){
		initialize(jsonManager, method, path);	
		super.setPostParam("groupName", name);
	}
}
