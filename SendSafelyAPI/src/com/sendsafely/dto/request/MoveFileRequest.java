package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class MoveFileRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/directory/" + GetParam.DIRECTORY_ID + "/file/"+ GetParam.FILE_ID;
	
	public MoveFileRequest(JsonManager jsonManager){
		initialize(jsonManager, method, path);	
	}

	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
		
	}

	public void setFileId(String fileId) {
		super.setGetParam(GetParam.FILE_ID, fileId);
		
	}
	public void setTargetDirectoryId(String targetDirectoryId){
		super.setGetParam(GetParam.DIRECTORY_ID, targetDirectoryId);
	}

}
