package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetOrganizationContactGroupsRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/enterprise/groups/";
	
	public GetOrganizationContactGroupsRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
}
