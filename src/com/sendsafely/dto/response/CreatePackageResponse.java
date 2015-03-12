package com.sendsafely.dto.response;

public class CreatePackageResponse extends BaseResponse {

	private String packageId;
	private String packageCode;
	private String serverSecret;
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getServerSecret() {
		return serverSecret;
	}
	public void setServerSecret(String serverSecret) {
		this.serverSecret = serverSecret;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	
	
	
}
