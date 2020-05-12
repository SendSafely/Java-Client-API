package com.sendsafely.dto.response;

import java.util.Date;
import java.util.List;

import com.sendsafely.ContactGroup;
import com.sendsafely.enums.PackageState;

public class PackageInformationResponse extends BaseResponse {

	private String packageId;
	private String packageCode;
	private String serverSecret;
	private String packageUserName;
	private String packageUserId;
	private List<RecipientResponse> recipients;
	private List<ContactGroup> contactGroups;
	private List<FileResponse> files;
	private List<DirectoryResponse> directories;
	private List<String> approverList;
	private boolean needsApproval;
	private PackageState state;
	private int life;
	private String description;
	private String label;
	private boolean isVDR;
	private String rootDirectoryId;
	private Date packageTimestamp;
	private String packageSender;
	private String packageParentId;
	
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
	public List<ContactGroup> getContactGroups() {
		return this.contactGroups;
	}
	
	public void setContactGroups(List<ContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}
	public String getPackageUserName() {
		return packageUserName;
	}
	public void setPackageUserName(String packageUserName) {
		this.packageUserName = packageUserName;
	}
	public String getPackageUserId() {
		return packageUserId;
	}
	public void setPackageUserId(String packageUserId) {
		this.packageUserId = packageUserId;
	}
	public List<DirectoryResponse> getDirectories() {
		return directories;
	}
	public void setDirectories(List<DirectoryResponse> directories) {
		this.directories = directories;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isVDR() {
		return isVDR;
	}
	public void setVDR(boolean isVDR) {
		this.isVDR = isVDR;
	}
	public String getRootDirectoryId() {
		return rootDirectoryId;
	}
	public void setRootDirectoryId(String rootDirectoryId) {
		this.rootDirectoryId = rootDirectoryId;
	}
	public Date getPackageTimestamp() {
		return packageTimestamp;
	}
	public void setPackageTimestamp(Date packageTimestamp) {
		this.packageTimestamp = packageTimestamp;
	}
	public String getPackageSender() {
		return packageSender;
	}
	public void setPackageSender(String packageSender) {
		this.packageSender = packageSender;
	}
	public String getPackageParentId() {
		return packageParentId;
	}
	public void setPackageParentId(String packageParentId) {
		this.packageParentId = packageParentId;
	}
}
