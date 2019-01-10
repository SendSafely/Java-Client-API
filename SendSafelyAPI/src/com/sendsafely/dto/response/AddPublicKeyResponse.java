package com.sendsafely.dto.response;

public class AddPublicKeyResponse extends BaseResponse {

	private String id;
    private String description;
    private String dateStr;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
    
    
}
