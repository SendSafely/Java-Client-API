package com.sendsafely;

public class Privatekey {

	private String privateKey;
    private String publicKeyId;
    
	public String getArmoredKey() {
		return privateKey;
	}
	public void setArmoredKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKeyId() {
		return publicKeyId;
	}
	public void setPublicKeyId(String publicKeyId) {
		this.publicKeyId = publicKeyId;
	}
}
