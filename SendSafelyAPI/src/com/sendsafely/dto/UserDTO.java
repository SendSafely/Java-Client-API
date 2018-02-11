package com.sendsafely.dto;

public class UserDTO {

	private String userEmail;
	private String userId;
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
