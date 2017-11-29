package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class UpdatePackageDescriptorRequest extends BaseRequest {
	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID;
	
	public UpdatePackageDescriptorRequest(JsonManager jsonManager){
		initialize(jsonManager, method, path);		
	}

	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}

	public void setPackageDescriptor(String packageDescriptor) {
		super.setPostParam("label", packageDescriptor);
	}
}
