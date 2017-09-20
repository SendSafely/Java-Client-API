package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetContactGroupsRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/user/groups/";
	
	public GetContactGroupsRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
}
