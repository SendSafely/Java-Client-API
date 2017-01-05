package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class DownloadFileRequest extends BaseRequest {

	protected HTTPMethod method = HTTPMethod.POST;
	protected String path = "/package/" + GetParam.PACKAGE_ID + "/file/" + GetParam.FILE_ID + "/download/";
	
	public DownloadFileRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public DownloadFileRequest(JsonManager jsonManager, HTTPMethod method, String path) {
		this.method = method;
		this.path = path;
		initialize(jsonManager, method, path);
	}
	
	public void setUploadType(String api) 
	{
		super.setPostParam("api", api);
	}
	
	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setFileId(String fileId) {
		super.setGetParam(GetParam.FILE_ID, fileId);
	}
	
	public void setChecksum(String checksum) {
		super.setPostParam("checksum", checksum);
	}
	
	public void setPart(int part) {
		super.setPostParam("part", part);
	}
	
	public void setApi(String api) {
		super.setPostParam("api", api);
	}
	public void setPassword(String password) {
		super.setPostParam("password", password);
	}
	
	@Override
	public String getPath()
	{
		return super.getPath();
	}
	
}
