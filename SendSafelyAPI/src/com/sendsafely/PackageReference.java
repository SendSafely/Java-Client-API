package com.sendsafely;

import java.util.List;

/**
 * A Java Bean containing information about a package. 
 * Only the Getters should be used from this object, since the server will populate the object. 
 * Updating the setters will not change any state on the server and should be avoided.
 *
 */
public class PackageReference extends BasePackage {

	private List<String> recipients;
	private List<String> contactGroupNames;
	
	/**
	 * @description Get all recipients that are currently associated with the package
	 * @returnType List<String>
	 * @return A list of recipient emails.
	 */
	public List<String> getRecipients() {
		return recipients;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param recipients
	 */
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	
	/**
	 * @description Get all contactGroup names that are currently associated with the package
	 * @returnType List<String>
	 * @return A list of contact group names.
	 */
	public List<String> getContactGroupNames() {
		return contactGroupNames;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param contactGroupNames
	 */
	public void setContactGroupNames(List<String> contactGroupNames) {
		this.contactGroupNames = contactGroupNames;
	}
}
