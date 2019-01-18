package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetDownloadUrlsRequest extends BaseRequest {

	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/file/" + GetParam.FILE_ID + "/download-urls/";
	
	public GetDownloadUrlsRequest(JsonManager jsonManager){
		initialize(jsonManager, method, path);
	}

	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setFileId(String fileId) {
		super.setGetParam(GetParam.FILE_ID, fileId);
	}
	
	public void setStartSegment(int start) {
		super.setPostParam("startSegment", start);
	}
	
	public void setEndSegment(int end) {
		super.setPostParam("endSegment", end);
	}

	public void setChecksum(String checksum){
		super.setPostParam("checksum", checksum);
	}
	public void setForceProxy(boolean forceProxy){
		super.setPostParam("forceProxy", forceProxy);
	}
}