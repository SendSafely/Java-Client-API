package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class CreateContactGroupRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/group/";
	
	public CreateContactGroupRequest(JsonManager jsonManager){
		initialize(jsonManager, method, path);	
	}
	
	public void setIsEnterpriseContactGroup(boolean isEnterpriseGroup){
		super.setPostParam("isEnterpriseGroup", isEnterpriseGroup);
	}
	
	public void setgroupName(String name){
		super.setPostParam("groupName", name);
	}
}
