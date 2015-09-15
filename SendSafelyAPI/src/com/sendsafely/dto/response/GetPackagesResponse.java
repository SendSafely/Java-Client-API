package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.enums.PackageState;

public class GetPackagesResponse extends BaseResponse {
	
	List<PackageListResponse> packages;

	/**
	 * @return the packages
	 */
	public List<PackageListResponse> getPackages() {
		return packages;
	}

	/**
	 * @param packages the packageList to set
	 */
	public void setPackages(List<PackageListResponse> packages) {
		this.packages = packages;
	}
	
	
	
}
