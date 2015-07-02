package com.sendsafely.dto.response;

public class AddRecipientResponse extends BaseResponse {

	private String recipientId;
	private String email;
	private boolean needsApproval;
	
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
	
	
	
}
