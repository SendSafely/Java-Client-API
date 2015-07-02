package com.sendsafely.dto.response;

public class UserInformationResponse extends BaseResponse {

	private String email;
	private String clientKey;
	private String firstName;
	private String lastName;
	
	/**
	 * The email address connected to the user.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set the email address for the connected user.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * The client key for the current user. The client key is a unique token for every user. 
	 * This token can be used to encrypt secrets belonging to the user.
	 * @return client key
	 */
	public String getClientKey() {
		return clientKey;
	}
	
	/**
	 * Set the client key.
	 * @param clientKey
	 */
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
	/**
	 * Get the first name of the current user.
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Set the first name of the current user.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Get the last name of the current user.
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Set the last name of the current user.
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
