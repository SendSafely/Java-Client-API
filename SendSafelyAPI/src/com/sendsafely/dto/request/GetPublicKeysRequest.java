package com.sendsafely.dto.request;

import com.sendsafely.enums.CountryCode;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetPublicKeysRequest extends BaseRequest
{

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/public-keys/";

	public GetPublicKeysRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}

	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
