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
	private List<Confirmation> confirmations = new ArrayList<Confirmation>();
	private String role;
	
	/**
	 * @description Get the unique recipient ID for the object. The recipient ID is unique for every new package. The same user will have different recipient IDs for different packages.
	 * @return
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
	 * @return
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
	 * @return
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
	 * @returnType Confirmation
	 * @return
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

	public String getRole() {
		return role;
	}

	public void setRole(String roleName) {
		this.role = roleName;
	}
	
}
