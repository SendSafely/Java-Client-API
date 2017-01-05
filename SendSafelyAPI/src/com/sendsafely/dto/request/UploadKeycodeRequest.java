package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class UploadKeycodeRequest extends BaseRequest {

	protected HTTPMethod method = HTTPMethod.PUT;
	protected String path = "/package/" + GetParam.PACKAGE_ID + "/link/" + GetParam.PUBLIC_KEY_ID + "/";

	public UploadKeycodeRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}

	public UploadKeycodeRequest(JsonManager jsonManager, HTTPMethod method, String path) {
		this.method = method;
		this.path = path;
		initialize(jsonManager, method, path);
	}

	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
    public void setPublicKeyId(String publicKeyId) {
        super.setGetParam(GetParam.PUBLIC_KEY_ID, publicKeyId);
    }
    public void setKeycode(String keycode) {
        super.setPostParam("keycode", keycode);
    }
	
	@Override
	public String getPath()
	{
		return super.getPath();
	}
	
}
