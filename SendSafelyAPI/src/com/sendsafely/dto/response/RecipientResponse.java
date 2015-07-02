package com.sendsafely.dto.response;

import java.util.List;

public class RecipientResponse {
	
	private String recipientId;
	private String email;
	private boolean needsApproval;
	private List<ConfirmationResponse> confirmations;
	
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
	public boolean getNeedsApproval() {
		return needsApproval;
	}
	public void setNeedsApproval(boolean needsApproval) {
		this.needsApproval = needsApproval;
	}
	public List<ConfirmationResponse> getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(List<ConfirmationResponse> confirmations) {
		this.confirmations = confirmations;
	}
	

}
