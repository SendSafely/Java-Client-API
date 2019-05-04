package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sendsafely.dto.request.BaseRequest;
import com.sendsafely.dto.request.GetDownloadUrlsFromDirectoryRequest;
import com.sendsafely.dto.request.GetDownloadUrlsRequest;
import com.sendsafely.dto.response.GetDownloadUrlsResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.GetDownloadUrlsException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetDownloadUrlsHandler extends BaseHandler {

	private BaseRequest request;
	private int totalFileParts;
	private int totalDownloadUrlsFetched = 0;
	private List<Map<String, String>> downloadUrls;
	private static int MAX_URL_BATCH_SIZE = 25;
	private static int URL_REFRESH_LIMIT = 10;
	
	public GetDownloadUrlsHandler(UploadManager uploadManager, int totalFileParts) {
		super(uploadManager);		
		this.totalFileParts = totalFileParts;
		this.downloadUrls = new ArrayList<Map<String, String>>();
	}
	
	public void fetchDownloadUrls(String packageId, String fileId, String directoryId, int filePart, boolean forceProxy, String password, String checksum) throws GetDownloadUrlsException {
		
		int urlBatchSize = 0;
		
		if (filePart == 1) {
			urlBatchSize = (this.totalFileParts < MAX_URL_BATCH_SIZE) ? this.totalFileParts :  MAX_URL_BATCH_SIZE;
			if(directoryId!=null){
				createRequest(packageId,fileId,directoryId,filePart,urlBatchSize, forceProxy, checksum);
			}else{
				createRequest(packageId,fileId,filePart,urlBatchSize, forceProxy, checksum, password);
			}
			makeRequest();
		} else if (filePart == (totalDownloadUrlsFetched - URL_REFRESH_LIMIT)) {
			//Refresh limit hit. Do we need more URLs?
			int urlsRemaining = this.totalFileParts - this.totalDownloadUrlsFetched;
			if (urlsRemaining > 0) {
				urlBatchSize = (urlsRemaining < MAX_URL_BATCH_SIZE) ? urlsRemaining :  MAX_URL_BATCH_SIZE;
				if(directoryId!=null){
					createRequest(packageId,fileId,directoryId,totalDownloadUrlsFetched+1,totalDownloadUrlsFetched+urlBatchSize, forceProxy, checksum);
				}else{
					createRequest(packageId,fileId,totalDownloadUrlsFetched+1,totalDownloadUrlsFetched+urlBatchSize, forceProxy, checksum, password);
				}
				makeRequest();
			}
		} else {
			//No new URLs need to be fetched
		}
	}
	
	private void makeRequest() throws GetDownloadUrlsException {
		
		GetDownloadUrlsResponse response = send();
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			this.totalDownloadUrlsFetched+=response.getDownloadUrls().size();
			this.downloadUrls.addAll(response.getDownloadUrls());
		}
		else
		{
			throw new GetDownloadUrlsException(response.getMessage());
		}
		
	}
	
	private void createRequest (String packageId, String fileId, int startSegment, int endSegment, boolean forceProxy, String checksum, String password) {
		
		this.request = new GetDownloadUrlsRequest(this.uploadManager.getJsonManager());
		((GetDownloadUrlsRequest) request).setPackageId(packageId);
		((GetDownloadUrlsRequest) request).setFileId(fileId);
		((GetDownloadUrlsRequest) request).setStartSegment(startSegment);
		((GetDownloadUrlsRequest) request).setEndSegment(endSegment);
		((GetDownloadUrlsRequest) request).setChecksum(checksum);
		((GetDownloadUrlsRequest) request).setForceProxy(forceProxy);
		if(password != null){
			((GetDownloadUrlsRequest) request).setPassword(password);
		}
	}
	private void createRequest (String packageId, String fileId, String directoryId, int startSegment, int endSegment, boolean forceProxy, String checksum) {
		
		this.request = new GetDownloadUrlsFromDirectoryRequest(this.uploadManager.getJsonManager());
		((GetDownloadUrlsFromDirectoryRequest) request).setPackageId(packageId);
		((GetDownloadUrlsFromDirectoryRequest) request).setFileId(fileId);
		((GetDownloadUrlsFromDirectoryRequest) request).setStartSegment(startSegment);
		((GetDownloadUrlsFromDirectoryRequest) request).setEndSegment(endSegment);
		((GetDownloadUrlsFromDirectoryRequest) request).setChecksum(checksum);
		((GetDownloadUrlsFromDirectoryRequest) request).setDirectoryId(directoryId);
		((GetDownloadUrlsFromDirectoryRequest) request).setForceProxy(forceProxy);
	}
	
	public void removeUrl() {
		this.downloadUrls.remove(0);
	}
	
	public List<Map<String, String>> getCurrentDownloadUrls () {
		return this.downloadUrls;
	}
	
	protected GetDownloadUrlsResponse send() throws GetDownloadUrlsException{
		try {
			return send(request, new GetDownloadUrlsResponse());
		} catch (SendFailedException e) {
			throw new GetDownloadUrlsException(e);
		} catch (IOException e) {
			throw new GetDownloadUrlsException(e);
		}
	}
}
