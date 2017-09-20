package com.sendsafely.dto.response;

import java.util.Collection;
import java.util.HashSet;

public class AddContactGroupAsRecipientResponse extends BaseResponse {
	private String contactGroupId;
	private String contactGroupName;
	private Collection<String> contactGroupUserEmails = (Collection<String>) new HashSet<String>(0);
	
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
	public Collection<String> getContactGroupUserEmails() {
		return contactGroupUserEmails;
	}
	public void setContactGroupUserEmails(Collection<String> contactGroupUsers) {
		this.contactGroupUserEmails = contactGroupUsers;
	}
}
