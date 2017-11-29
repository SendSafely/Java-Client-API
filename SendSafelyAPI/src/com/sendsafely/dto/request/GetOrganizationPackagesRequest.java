package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.enums.PackageStatus;
import com.sendsafely.json.JsonManager;

public class GetOrganizationPackagesRequest  extends BaseRequest 
{	
	private HTTPMethod method = HTTPMethod.POST;
  	private String path = "/package/organization/";

	public GetOrganizationPackagesRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setFromDate(String fromDate) {
		super.setPostParam("fromDate", fromDate);
	}
	
	public void setToDate(String toDate) {
		super.setPostParam("toDate", toDate);
	}
	
	public void setSender(String sender) {
		super.setPostParam("sender", sender);
	}
	
	public void setStatus(String status) {
		super.setPostParam("status", status);
	}
	
	public void setRecipient(String recipient) {
		super.setPostParam("recipient", recipient);
	}
	
	public void setFileName(String filename) {
		super.setPostParam("filename", filename);
	}
}
