package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.Phonenumber;

public class AddRecipientResponse extends BaseResponse {

	private String recipientId;
	private String email;
	private boolean approvalRequired;
	private List<String> approvers;
    private List<Phonenumber> phonenumbers;
	
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getApprovalRequired() {
		return approvalRequired;
	}
	public void setApprovalRequired(boolean approvalRequired) {
		this.approvalRequired = approvalRequired;
	}
	public List<String> getApprovers() {
		return approvers;
	}
	public void setApprovers(List<String> approvers) {
		this.approvers = approvers;
	}
	public List<Phonenumber> getPhonenumbers() {
		return phonenumbers;
	}
	public void setPhonenumbers(List<Phonenumber> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}
	
}
