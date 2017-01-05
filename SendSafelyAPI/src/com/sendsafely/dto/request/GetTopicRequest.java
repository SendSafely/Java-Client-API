package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetTopicRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/topic/" + GetParam.TOPIC_ID + "/";
	
	public GetTopicRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setTopicId(String topicId)
	{
		super.setGetParam(GetParam.TOPIC_ID, topicId);
	}
	
}
