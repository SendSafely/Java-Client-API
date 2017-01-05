package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetFileHistoryRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/file/" + GetParam.FILE_ID + "/history/";
	
	public GetFileHistoryRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setFileId(String fileId)
	{
		super.setGetParam(GetParam.FILE_ID, fileId);
	}
	
}
