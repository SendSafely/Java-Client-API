package com.sendsafely;

import java.util.List;

public class PackageSearchResults {

	List<PackageReference> packages;
	boolean capped;
	public List<PackageReference> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageReference> packages) {
		this.packages = packages;
	}
	public boolean isCapped() {
		return capped;
	}
	public void setCapped(boolean limit) {
		this.capped = limit;
	}
}
