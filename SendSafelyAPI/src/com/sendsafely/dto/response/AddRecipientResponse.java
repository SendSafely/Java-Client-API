package com.sendsafely.dto.response;

public class AddRecipientResponse extends BaseResponse {

	private String recipientId;
	private String email;
	private boolean approvalRequired;
	private boolean canAddFiles;
	private boolean canAddMessages;
	private boolean canAddRecipients;
	
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
	public boolean getCanAddFiles() {
		return canAddFiles;
	}
	public void setCanAddFiles(boolean canAddFiles) {
		this.canAddFiles = canAddFiles;
	}
	public boolean getCanAddMessages() {
		return canAddMessages;
	}
	public void setCanAddMessages(boolean canAddMessages) {
		this.canAddMessages = canAddMessages;
	}
	public boolean getCanAddRecipients() {
		return canAddRecipients;
	}
	public void setCanAddRecipients(boolean canAddRecipients) {
		this.canAddRecipients = canAddRecipients;
	}
	
	
	
}
