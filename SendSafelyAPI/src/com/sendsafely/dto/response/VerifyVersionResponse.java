package com.sendsafely.dto.response;

import com.sendsafely.enums.Version;

public class VerifyVersionResponse extends BaseResponse {
	
	private Version version;

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	
	
}
