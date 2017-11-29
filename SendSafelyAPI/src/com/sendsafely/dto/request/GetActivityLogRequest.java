package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetActivityLogRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/activityLog/";
	
	public GetActivityLogRequest(JsonManager jsonManager){
		initialize(jsonManager, method, path);
	}

	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}

	public void setRowIndex(int rowIndex) {
		super.setPostParam("rowIndex", rowIndex);
	}
}
