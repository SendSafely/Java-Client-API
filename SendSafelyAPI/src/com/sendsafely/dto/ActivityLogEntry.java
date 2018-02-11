package com.sendsafely.dto;

import java.util.Date;

public class ActivityLogEntry {

	private String activityLogId;
	private Date timestamp;
	private String timestampStr;
	private String ipAddress;
	private String packageId;
	private String targetId;
	private String actionDescription;
	private String action;
	private UserDTO user;
	

	/**
	 * @returnType String
	 * @return
	 */
	public String getPackageId(){
		return this.packageId;
	}
	
	public void setPackageId(String packageId){
		this.packageId = packageId;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getActivityLogId() {
		return activityLogId;
	}

	public void setActivityLogId(String activityLogId) {
		this.activityLogId = activityLogId;
	}

	/**
	 * @returnType Date
	 * @return
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @returnType UserDTO
	 * @return
	 */
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getTimestampStr() {
		return timestampStr;
	}

	public void setTimestampStr(String timestampStr) {
		this.timestampStr = timestampStr;
	}
}
