package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetKeycodeRequest extends BaseRequest
{

	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/link/" + GetParam.PUBLIC_KEY_ID + "/";

	public GetKeycodeRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}

	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}

    public void setPublicKeyId(String publicKeyId)
    {
        super.setGetParam(GetParam.PUBLIC_KEY_ID, publicKeyId);
    }
	
}
