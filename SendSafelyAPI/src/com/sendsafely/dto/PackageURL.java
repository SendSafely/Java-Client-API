package com.sendsafely.dto;

import java.net.URL;

public class PackageURL {

	private URL secureLink;
	private String keycode;
	private boolean needsApproval;
	private String notificationStatus = "DISABLED";
	
	/**
	 * @returnType URL
	 * @description Returns the secure link that must be sent to the recipients. 
	 * @return the secure link
	 */
	public URL getSecureLink() {
		return secureLink;
	}
	/**
	 * @description Set internally by the API.
	 * @param url the url to set
	 */
	public void setSecureLink(URL secureLink) {
		this.secureLink = secureLink;
	}
	/**
	 * @returnType String
	 * @description Returns the keycode
	 * @return the keycode
	 */
	public String getKeycode() {
		return keycode;
	}
	/**
	 * @description Set internally by the API.
	 * @param keycode the keycode to set
	 */
	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}
	/**
	 * @returnType boolean
	 * @description Returns true if the package needs approval before it can be accessed by all recipients.
	 * @return the needsApproval flag indicating if the package needs approval before it can be sent to all recipients
	 */
	public boolean needsApproval() {
		return needsApproval;
	}
	/**
	 * @description Set internally by the API.
	 * @param needsApproval the needsApproval to set
	 */
	public void setNeedsApproval(boolean needsApproval) {
		this.needsApproval = needsApproval;
	}
	
	/**
	 * @returnType String
	 * @description Returns status DISABLED by default, SUCCESS if package email notification sent successfully, otherwise returns error message. 
	 * @return notificationStatus message indicating status of package email notification
	 */
	public String getNotificationStatus() {
		return notificationStatus;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param notificationStatus the status message indication success or error
	 */
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	
	
	
}
