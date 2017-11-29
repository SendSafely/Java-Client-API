package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class MoveDirectoryRequest extends BaseRequest {
	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/move/" + GetParam.SOURCE_DIRECTORY_ID + "/" + GetParam.TARGET_DIRECTORY_ID + "/";
	
	public MoveDirectoryRequest(JsonManager jsonManager){
		initialize(jsonManager, method, path);		
	}
	
	public void setTargetDirectoryId(String targetDirectoryId){
		super.setGetParam(GetParam.TARGET_DIRECTORY_ID, targetDirectoryId);
	}
	
	public void setPackageId(String packageId){
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setSourceDirectoryId(String sourceDirectoryId){
		super.setGetParam(GetParam.SOURCE_DIRECTORY_ID, sourceDirectoryId);
	}
	
}
