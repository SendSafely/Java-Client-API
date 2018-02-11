package com.sendsafely;

import java.util.List;

/**
 * A Java Bean containing information about a package. 
 * Only the Getters should be used from this object, since the server will populate the object. 
 * Updating the setters will not change any state on the server and should be avoided.
 *
 */
public class Package extends BasePackage {

	private List<Recipient> recipients;
	
	private List<ContactGroup> contactGroups;
	/**
	 * @description Get all recipients that are currently associated with the package
	 * @returnType List<Recipient>
	 * @return A list of recipients.
	 */
	public List<Recipient> getRecipients() {
		return recipients;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param recipients
	 */
	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @description Set internally by the API.
	 * @returnType List<ContactGroup>
	 * @return A list of contact groups.
	 */
	public List<ContactGroup> getContactGroups() {
		return contactGroups;
	}

	/**
	 * @description Set internally by the API.
	 * @param contactGroups
	 */
	public void setContactGroups(List<ContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}	
	
}
