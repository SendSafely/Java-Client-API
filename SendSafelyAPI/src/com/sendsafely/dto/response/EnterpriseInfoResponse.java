package com.sendsafely.dto.response;

public class EnterpriseInfoResponse extends BaseResponse {

	private String host;
	private String systemName;
	private Boolean outlookBeta;
	private Boolean allowUndisclosedRecipients;
	private String headerColor;
	private String linkColor;
	private boolean messageEncryption;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public Boolean getOutlookBeta() {
		return outlookBeta;
	}
	public void setOutlookBeta(Boolean outlookBeta) {
		this.outlookBeta = outlookBeta;
	}
	public Boolean getAllowUndisclosedRecipients() {
		return allowUndisclosedRecipients;
	}
	public void setAllowUndisclosedRecipients(Boolean allowUndisclosedRecipients) {
		this.allowUndisclosedRecipients = allowUndisclosedRecipients;
	}
	public String getHeaderColor() {
		return headerColor;
	}
	public void setHeaderColor(String headerColor) {
		this.headerColor = headerColor;
	}
	public String getLinkColor() {
		return linkColor;
	}
	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}
	public boolean getMessageEncryption() {
		return this.messageEncryption;
	}
	public void setMessageEncryption(boolean messageEncryption) {
		this.messageEncryption = messageEncryption;
	}
	
}
