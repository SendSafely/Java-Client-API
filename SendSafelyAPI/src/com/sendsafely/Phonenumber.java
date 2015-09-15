package com.sendsafely;

public class Phonenumber {

	private String phonenumber;
	private int countryCode;
	private boolean wasUsedMostRecently;
	
	public Phonenumber(String phoneNumber, int countryCode, boolean wasMostRecent) {
		this.setPhonenumber(phoneNumber);
		this.setCountryCode(countryCode);
		this.setWasUsedMostRecently(wasMostRecent);
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	public Boolean getWasUsedMostRecently() {
		return wasUsedMostRecently;
	}

	public void setWasUsedMostRecently(Boolean wasUsedMostRecently) {
		this.wasUsedMostRecently = wasUsedMostRecently;
	}
	
	
	
}
