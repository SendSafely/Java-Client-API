package com.sendsafely.dto;

public class UserInformation {

	private String email;
	private String clientKey;
	private String firstName;
	private String lastName;
	
	/**
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
	
}
