package com.sendsafely.dto.response;

import java.util.ArrayList;
import java.util.List;


public class GetDropzoneRecipientResponse extends BaseResponse {

	private List<String> recipientEmailAddresses = new ArrayList<String>();

	public List<String> getRecipientEmailAddresses() {
		return recipientEmailAddresses;
	}

	public void setRecipientUsers(List<String> recipientUsers) {
		this.recipientEmailAddresses.clear();
		for (String u : recipientUsers)
		{
			this.recipientEmailAddresses.add(u);
		}
	}
}
