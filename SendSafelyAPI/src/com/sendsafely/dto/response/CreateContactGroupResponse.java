package com.sendsafely.dto.response;

import java.util.List;

public class CreateContactGroupResponse extends BaseResponse {
	private String contactGroupId;
	private String contactGroupName;
	
	public String getContactGroupId() {
		return contactGroupId;
	}
	public void setContactGroupId(String contactGroupId) {
		this.contactGroupId = contactGroupId;
	}
	public String getContactGroupName() {
		return contactGroupName;
	}
	public void setContactGroupName(String contactGroupName) {
		this.contactGroupName = contactGroupName;
	}	
}
