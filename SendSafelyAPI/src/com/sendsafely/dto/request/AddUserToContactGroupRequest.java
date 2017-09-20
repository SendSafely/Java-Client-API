package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class AddUserToContactGroupRequest extends BaseRequest {
	private HTTPMethod method = HTTPMethod.PUT;
	protected String path = "/group/" + GetParam.GROUP_ID + "/user/";
	
	public void setGroupId(String groupId) {
		super.setGetParam(GetParam.GROUP_ID, groupId);
	}

	public AddUserToContactGroupRequest(JsonManager jsonManager, String groupId, String userEmail){
		initialize(jsonManager, method, path);
		super.setGetParam(GetParam.GROUP_ID, groupId);
		super.setPostParam("userEmail", userEmail);
	}
}
