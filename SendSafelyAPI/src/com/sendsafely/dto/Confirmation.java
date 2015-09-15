package com.sendsafely.dto;

import java.util.Date;

import com.sendsafely.File;

/**
 * @description A Java Bean containing information about a Confirmation. A confirmation contains information about when a file was downloaded by a recipient. Only the Getters should be used from this object, since the server will populate the object. Updating the setters will not change any state on the server and should be avoided.
 * @author Erik Larsson
 *
 */
public class Confirmation {

	private String ipAddress;
	private Date timestamp;
	private File file;
	private boolean isMessage = false;
	
	/**
	 * @description Get the IPAddress from where the file was downloaded from.
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @description Set internally by the API.
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @description Get the time when the file or message was downloaded.
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @description Set internally by the API.
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @description A File object referencing the file that was downloaded. Will be null if the confirmation refers to a message. 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @description Set internally by the API.
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @description A flag indicating if the confirmation referes to a message. If true, the file object will be null.
	 * @return the isMessage
	 */
	public boolean isMessage() {
		return isMessage;
	}
	/**
	 * @description Set internally by the API.
	 * @param isMessage the isMessage to set
	 */
	public void setMessage(boolean isMessage) {
		this.isMessage = isMessage;
	}
	
	
	
	
	
}
