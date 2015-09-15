package com.sendsafely.dto.response;

public class EnterpriseInfoResponse extends BaseResponse {

	private String host;
	private String systemName;
	private Boolean outlookBeta;
	private Boolean allowUndisclosedRecipients;
	
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
	
	
	
	
}
