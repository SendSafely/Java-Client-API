package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class RemoveUserFromContactGroupRequest extends BaseRequest {
	
	private HTTPMethod method = HTTPMethod.DELETE;
	protected String path = "/group/" + GetParam.GROUP_ID + "/"+ GetParam.USER_ID +"/";

	public RemoveUserFromContactGroupRequest(JsonManager jsonManager, String groupId, String userId) {
		initialize(jsonManager, method, path);
		super.setGetParam(GetParam.GROUP_ID, groupId);
		super.setGetParam(GetParam.USER_ID, userId);
	}
	
	public RemoveUserFromContactGroupRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setGroupId(String groupId) {
		super.setGetParam(GetParam.GROUP_ID, groupId);
	}
	public void setUserId(String userId) {
		super.setGetParam(GetParam.USER_ID, userId);
	}

}
