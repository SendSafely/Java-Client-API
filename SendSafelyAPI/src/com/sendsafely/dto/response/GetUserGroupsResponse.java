package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.ContactGroup;


public class GetUserGroupsResponse extends BaseResponse {
	private List<ContactGroup> contactGroups;

	public List<ContactGroup> getContactGroups() {
		return contactGroups;
	}

	public void setContactGroups(List<ContactGroup> userContactGroups) {
		this.contactGroups = userContactGroups;
	}
}
