package com.sendsafely.dto.response;

import java.util.List;
import java.util.Map;

public class GetDownloadUrlsResponse extends BaseResponse {
	
	private List<Map<String, String>> downloadUrls;

    public List<Map<String, String>> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(List<Map<String, String>> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

}