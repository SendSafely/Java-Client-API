package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sendsafely.dto.ActivityLogEntry;
import com.sendsafely.dto.request.GetActivityLogRequest;
import com.sendsafely.dto.response.GetActivityLogResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.GetActivityLogException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetActivityLogHandler extends BaseHandler {

private GetActivityLogRequest request;
	
	protected GetActivityLogHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new GetActivityLogRequest(this.uploadManager.getJsonManager());
	}
	
	public List<ActivityLogEntry> makeRequest(String packageId, int rowIndex) throws GetActivityLogException {
		request.setPackageId(packageId);
		request.setRowIndex(rowIndex);
		GetActivityLogResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new GetActivityLogException(response.getMessage());
		}
		return convert(response);
	}
	
	private List<ActivityLogEntry> convert(GetActivityLogResponse response) {
		List<ActivityLogEntry> entries = new ArrayList<ActivityLogEntry>();
		entries = (List<ActivityLogEntry>) response.getActivityLogEntries();
		return entries;
	}

	protected GetActivityLogResponse send() throws GetActivityLogException 
	{
		try {
			return send(request, new GetActivityLogResponse());
		} catch (IOException | SendFailedException e) {
			throw new GetActivityLogException(e.getMessage());
		}
	}
}
