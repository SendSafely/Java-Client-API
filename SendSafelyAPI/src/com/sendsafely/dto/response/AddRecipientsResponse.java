package com.sendsafely.dto.response;

import java.util.List;

public class AddRecipientsResponse extends BaseResponse {

	private boolean approvalRequired;
	private List<RecipientResponse> recipients;
	private List<String> approvers;
	
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
	public List<RecipientResponse> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<RecipientResponse> recipients) {
		this.recipients = recipients;
	}
	
}
