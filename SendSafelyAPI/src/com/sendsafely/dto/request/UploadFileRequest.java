package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class UploadFileRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/file/" + GetParam.FILE_ID + "/";
	
	public UploadFileRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setUploadType(String uploadType) 
	{
		super.setPostParam("uploadType", uploadType);
	}
	public void setFilePart(String filePart) {
		super.setPostParam("filePart", filePart);
	}
	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	public void setFileId(String fileId) {
		super.setGetParam(GetParam.FILE_ID, fileId);
	}
	
}
