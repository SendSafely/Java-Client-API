package com.sendsafely.dto;

import java.util.Date;
import java.util.Set;

import com.sendsafely.Recipient;

public class RecipientHistory {

	private String packageId;
	private String packageUserName;
	private String packageUserId;
	private int packageState;
	private String packageStateStr;
	private String packageStateColor;
	private int packageLife;
	private String packageUpdateTimestampStr;
	private Date packageUpdateTimestamp;
	private String packageCode;
	private String packageOS;
	private Recipient packageRecipientResponse;
	private Set<String> filenames;
	private boolean packageContainsMessage;
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
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
	public int getPackageState() {
		return packageState;
	}
	public void setPackageState(int packageState) {
		this.packageState = packageState;
	}
	public String getPackageStateStr() {
		return packageStateStr;
	}
	public void setPackageStateStr(String packageStateStr) {
		this.packageStateStr = packageStateStr;
	}
	public String getPackageStateColor() {
		return packageStateColor;
	}
	public void setPackageStateColor(String packageStateColor) {
		this.packageStateColor = packageStateColor;
	}
	public int getPackageLife() {
		return packageLife;
	}
	public void setPackageLife(int packageLife) {
		this.packageLife = packageLife;
	}
	public String getPackageUpdateTimestampStr() {
		return packageUpdateTimestampStr;
	}
	public void setPackageUpdateTimestampStr(String packageUpdateTimestampStr) {
		this.packageUpdateTimestampStr = packageUpdateTimestampStr;
	}
	public Date getPackageUpdateTimestamp() {
		return packageUpdateTimestamp;
	}
	public void setPackageUpdateTimestamp(Date packageUpdateTimestamp) {
		this.packageUpdateTimestamp = packageUpdateTimestamp;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getPackageOS() {
		return packageOS;
	}
	public void setPackageOS(String packageOS) {
		this.packageOS = packageOS;
	}
	public Recipient getPackageRecipientResponse() {
		return packageRecipientResponse;
	}
	public void setPackageRecipientResponse(Recipient packageRecipientResponse) {
		this.packageRecipientResponse = packageRecipientResponse;
	}
	public Set<String> getFilenames() {
		return filenames;
	}
	public void setFilenames(Set<String> filenames) {
		this.filenames = filenames;
	}
	public boolean isPackageContainsMessage() {
		return packageContainsMessage;
	}
	public void setPackageContainsMessage(boolean packageContainsMessage) {
		this.packageContainsMessage = packageContainsMessage;
	}
	
	
}
