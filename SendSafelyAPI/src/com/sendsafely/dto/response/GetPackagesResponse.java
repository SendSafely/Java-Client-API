package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.enums.PackageState;

public class GetPackagesResponse extends BaseResponse {
	
	List<String> packages;

	/**
	 * @return the packages
	 */
	public List<String> getPackages() {
		return packages;
	}

	/**
	 * @param packages the packageList to set
	 */
	public void setPackages(List<String> packages) {
		this.packages = packages;
	}
	
	
	
}
