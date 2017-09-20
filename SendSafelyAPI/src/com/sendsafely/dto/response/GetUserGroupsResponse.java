package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.ContactGroup;


public class GetUserGroupsResponse extends BaseResponse {
	private List<ContactGroup> userContactGroups;

	public List<ContactGroup> getUserContactGroups() {
		return userContactGroups;
	}

	public void setUserContactGroups(List<ContactGroup> userContactGroups) {
		this.userContactGroups = userContactGroups;
	}
}
