package com.sendsafely;

import java.util.List;

public class PackageSearchResults {

	List<PackageReference> packages;
	boolean capped;
	
	/**
	 * @returnType List<PackageReference>
	 * @return
	 */
	public List<PackageReference> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageReference> packages) {
		this.packages = packages;
	}
	
	/**
	 * @returnType boolean
	 * @return
	 */
	public boolean isCapped() {
		return capped;
	}
	public void setCapped(boolean limit) {
		this.capped = limit;
	}
}
