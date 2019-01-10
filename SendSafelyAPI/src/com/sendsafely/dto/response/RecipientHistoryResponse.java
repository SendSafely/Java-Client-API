package com.sendsafely.dto.response;

import java.util.List;

import com.sendsafely.dto.RecipientHistory;

public class RecipientHistoryResponse extends BaseResponse {

	private List<RecipientHistory> packages;
	public List<RecipientHistory> getPackages() {
		return packages;
	}

	public void setPackages(List<RecipientHistory> packages) {
		this.packages = packages;
	}
}
