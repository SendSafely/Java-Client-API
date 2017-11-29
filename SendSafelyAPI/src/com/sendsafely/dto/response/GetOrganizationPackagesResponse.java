package com.sendsafely.dto.response;

import java.util.List;

public class GetOrganizationPackagesResponse extends BaseResponse{

	private List<PackageListResponse> packages;
	private boolean capped;
	public List<PackageListResponse> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageListResponse> packages) {
		this.packages = packages;
	}
	public boolean isCapped() {
		return capped;
	}
	public void setCapped(boolean capped) {
		this.capped = capped;
	}
	
	
}
