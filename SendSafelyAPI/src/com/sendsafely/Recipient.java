package com.sendsafely;

import java.util.ArrayList;
import java.util.List;

import com.sendsafely.dto.Confirmation;

/**
 * A Java Bean containing information about a recipient. 
 * Only the Getters should be used from this object, since the server will populate the object. 
 * Updating the setters will not change any state on the server and should be avoided.
 *
 */
public class Recipient {

	private String recipientId;
	private String email;
	private boolean needsApproval;
	private List<String> approvers;
    private List<Phonenumber> phonenumbers;
	private List<Confirmation> confirmations = new ArrayList<Confirmation>();
	private String role;
	
	/**
	 * @description Get the unique recipient ID for the object. The recipient ID is unique for every new package. The same user will have different recipient IDs for different packages.
	 * @returnType String
	 * @return A string representing recipient id. 
	 */
	public String getRecipientId() {
		return recipientId;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param recipientId
	 */
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	
	/**
	 * @description Get the email address for the given recipient.
	 * @returnType String
	 * @return an email address string.
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
	 * @description Returns true if this recipient requires approval before the package can be retrieved.
	 * @returnType boolean
	 * @return A boolean representing that the recipient needs approval.
	 */
	public boolean getNeedsApproval() {
		return needsApproval;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param needsApproval
	 */
	public void setNeedsApproval(boolean needsApproval) {
		this.needsApproval = needsApproval;
	}
	
	/**
	 * @description Returns the list of confirmations.
	 * @returnType List<Confirmation>
	 * @return A list of confirmations.
	 */
	public List<Confirmation> getConfirmations() {
		return confirmations;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param confirmations
	 */
	public void setConfirmations(List<Confirmation> confirmations) {
		this.confirmations = confirmations;
	}

	/**
	 * @description Retrieves the role of the recipient
	 * @returnType String
	 * @return A String of the role value.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @description Sets the role for the recipient
	 * @param roleName Name of the role for the recipient
	 */
	public void setRole(String roleName) {
		this.role = roleName;
	}

	public List<String> getApprovers() {
		return approvers;
	}

	public void setApprovers(List<String> approvers) {
		this.approvers = approvers;
	}

	public List<Phonenumber> getPhonenumbers() {
		return phonenumbers;
	}

	public void setPhonenumbers(List<Phonenumber> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}
	
	
}
