package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class RemoveContactGroupRequest extends BaseRequest {
	
	private HTTPMethod method = HTTPMethod.DELETE;
	protected String path = "/group/" + GetParam.GROUP_ID + "/";
	
	public RemoveContactGroupRequest(JsonManager jsonManager, String groupId) {
		initialize(jsonManager, method, path);
		super.setGetParam(GetParam.GROUP_ID, groupId);
	}
	
	public RemoveContactGroupRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
}
