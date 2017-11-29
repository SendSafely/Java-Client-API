package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class DownloadFileFromDirectoryRequest extends BaseRequest{

	protected HTTPMethod method = HTTPMethod.POST;
	protected String path = "/package/" + GetParam.PACKAGE_ID + "/directory/" + GetParam.DIRECTORY_ID + "/file/" + GetParam.FILE_ID + "/download/";
	
	public DownloadFileFromDirectoryRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public DownloadFileFromDirectoryRequest(JsonManager jsonManager, HTTPMethod method, String path) {
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
	
	public void setDirectoryId(String directoryId) {
		super.setGetParam(GetParam.DIRECTORY_ID, directoryId);
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
