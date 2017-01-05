package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.enums.PackageState;

public class PackageInformationResponse extends BaseResponse {

	private String packageId;
	private String packageCode;
	private String serverSecret;
	private String packageUserName;
	private String packageUserId;
	private List<RecipientResponse> recipients;
	private List<FileResponse> files;
	private List<String> approverList;
	private boolean needsApproval;
	private PackageState state;
	private int life;
	private String description;
	
	public List<RecipientResponse> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<RecipientResponse> recipients) {
		this.recipients = recipients;
	}
	public List<FileResponse> getFiles() {
		return files;
	}
	public void setFiles(List<FileResponse> files) {
		this.files = files;
	}
	public boolean getNeedsApproval() {
		return needsApproval;
	}
	public void setNeedsApproval(boolean needsApproval) {
		this.needsApproval = needsApproval;
	}
	public PackageState getState() {
		return state;
	}
	public void setState(PackageState state) {
		this.state = state;
	}
	public List<String> getApproverList() {
		return approverList;
	}
	public void setApproverList(List<String> approverList) {
		this.approverList = approverList;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getServerSecret() {
		return serverSecret;
	}
	public void setServerSecret(String serverSecret) {
		this.serverSecret = serverSecret;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
