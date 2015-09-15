package com.sendsafely.dto.response;

public class AddRecipientResponse extends BaseResponse {

	private String recipientId;
	private String email;
	private boolean needsApproval;
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
	public boolean getNeedsApproval() {
		return needsApproval;
	}
	public void setNeedsApproval(boolean needsApproval) {
		this.needsApproval = needsApproval;
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
