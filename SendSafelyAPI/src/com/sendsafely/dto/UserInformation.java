package com.sendsafely.dto;

public class UserInformation {

	private String email;
	private String clientKey;
	private String firstName;
	private String lastName;
	private boolean betaUser;
	private boolean adminUser;
	
	/**
	 * @returnType String
	 * @description The email address connected to the user.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @returnType String
	 * @description The client key for the current user. The client key is a unique token for every user. This token can be used to encrypt secrets belonging to the user.
	 * @return client key
	 */
	public String getClientKey() {
		return clientKey;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param clientKey
	 */
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
	/**
	 * @returnType String
	 * @description Get the first name of the current user.
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @returnType String
	 * @description Get the last name of the current user.
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @returnType boolean
	 * @description Flag to determine if user is eligible to receive beta features.
	 * @param betaUser
	 */
	public boolean getBetaUser() {
		return betaUser;
	}

	/**
	 * @description Set internally by the API.
	 * @param betaUser
	 */
	public void setBetaUser(boolean betaUser) {
		this.betaUser = betaUser;
	}

	/**
	 * @returnType boolean
	 * @description Set internally by the API.
	 * @return isAdmin
	 */
	public boolean getAdminUser() {
		return adminUser;
	}

	/**
	 * @description Set internally by the API
	 * @param isAdmin
	 */
	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}
	
	
	
}
