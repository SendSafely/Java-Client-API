package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class CreateFileIdRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/file/";
	
	public CreateFileIdRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	
		super.setPostParam("uploadType", "JAVA_API");
	}
	
	public void setFilePart(String filePart) {
		super.setPostParam("filePart", filePart);
	}
	
	public void setFilename(String filename)
	{
		super.setPostParam("filename", filename);
	}
	
	public void setParts(int parts)
	{
		super.setPostParam("parts", parts);
	}
	
	public void setFilesize(long filesize)
	{
		super.setPostParam("filesize", filesize);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
