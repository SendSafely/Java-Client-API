package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.Phonenumber;


public class RecipientResponse extends BaseResponse {
	
	private String recipientId;
	private String email;
	private Boolean needsApproval;
	private List<Phonenumber> phonenumbers;
	private List<ConfirmationResponse> confirmations;
	private String autoEnabledNumber;
	
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
	public Boolean getNeedsApproval() {
		return needsApproval;
	}
	public void setNeedsApproval(Boolean needsApproval) {
		this.needsApproval = needsApproval;
	}
	public List<Phonenumber> getPhonenumbers() {
		return phonenumbers;
	}
	public void setPhonenumbers(List<Phonenumber> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}
	public String getAutoEnabledNumber() {
		return autoEnabledNumber;
	}
	public void setAutoEnabledNumber(String autoEnabledNumber) {
		this.autoEnabledNumber = autoEnabledNumber;
	}
	public List<ConfirmationResponse> getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(List<ConfirmationResponse> confirmations) {
		this.confirmations = confirmations;
	}
	
}
