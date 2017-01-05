package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetMessageRequest extends BaseRequest {
	
	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/message/" + GetParam.CHECKSUM + "/";
	
	public GetMessageRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setChecksum(String checksum)
	{
		super.setGetParam(GetParam.CHECKSUM, checksum);
	}
	
}
