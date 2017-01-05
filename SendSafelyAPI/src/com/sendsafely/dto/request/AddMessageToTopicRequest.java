package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class AddMessageToTopicRequest extends AddMessageRequest {
	
	//private static HTTPMethod method = HTTPMethod.PUT;
	//private static String path = "/package/" + GetParam.PACKAGE_ID + "/topic/" + GetParam.TOPIC_ID + "/message";
	private String topicId;
	
	public AddMessageToTopicRequest(JsonManager jsonManager, String topicId) {
		super(jsonManager, HTTPMethod.PUT, "/package/" + GetParam.PACKAGE_ID + "/topic/" + GetParam.TOPIC_ID + "/message");
		
		this.topicId = topicId;
		super.setGetParam(GetParam.TOPIC_ID, topicId);
	}
	
	public void setUploadType(String uploadType) 
	{
		super.setPostParam("uploadType", uploadType);
	}
	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	public void setMessage(String message) {
		super.setPostParam("message", message);
	}
	
}
