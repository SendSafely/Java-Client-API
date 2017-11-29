package com.sendsafely.dto.response;

import java.util.Date;
import java.util.List;

import com.sendsafely.enums.PackageState;

public class PackageListResponse extends BaseResponse {

	private String packageId;
	private String packageCode;
	private String serverSecret;
	private List<String> recipients;
	private List<String> contactGroups;
	private List<FileResponse> files;
	private List<String> filenames;
	private List<String> approverList;
	private boolean needsApproval;
	private PackageState state;
	private int packageState;
	private int life;
	private String description;
	private String packageUserName;
	private Date packageUpdateTimestamp;
	
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	public List<String> getContactGroups() {
		return contactGroups;
	}
	public void setContactGroups(List<String> contactGroups) {
		this.contactGroups = contactGroups;
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
	public String getPackageUserName() {
		return packageUserName;
	}
	public void setPackageUserName(String packageOwner) {
		if(packageOwner == null){
			this.packageUserName = "";
		}else{
			this.packageUserName = packageOwner;
		}	
	}
	public Date getPackageUpdateTimestamp() {
		return packageUpdateTimestamp;
	}
	public void setPackageUpdateTimestamp(Date packageUpdateTimestamp) {
		this.packageUpdateTimestamp = packageUpdateTimestamp;
	}
	public List<String> getFilenames() {
		return filenames;
	}
	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}
	public int getPackageState() {
		return packageState;
	}
	public void setPackageState(int packageState) {
		this.packageState = packageState;
	}

	
	
}
