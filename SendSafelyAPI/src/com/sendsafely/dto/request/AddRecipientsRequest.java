package com.sendsafely.dto.request;

import java.util.List;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class AddRecipientsRequest extends AddRecipientRequest 
{	
	
	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/recipients/";
	
	public AddRecipientsRequest(List<String> emails) {
		initialize(method, path);
	
		super.setPostParam("emails", emails);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
