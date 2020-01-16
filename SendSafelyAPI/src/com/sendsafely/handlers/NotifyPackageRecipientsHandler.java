package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.NotifyPackageRecipientsRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.NotifyPackageRecipientsException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class NotifyPackageRecipientsHandler extends BaseHandler 
{	
	
	private NotifyPackageRecipientsRequest request;
	
	public NotifyPackageRecipientsHandler(UploadManager uploadManager, String keycode) {
		super(uploadManager);
		this.request = new NotifyPackageRecipientsRequest(uploadManager.getJsonManager(),keycode);
	}

	public BaseResponse makeRequest(String packageId) throws NotifyPackageRecipientsException {
		request.setPackageId(packageId);

		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new NotifyPackageRecipientsException(response.getMessage());
		}
		
		return response;
	}
	
	protected BaseResponse send() throws NotifyPackageRecipientsException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new NotifyPackageRecipientsException(e);
		} catch (IOException e) {
			throw new NotifyPackageRecipientsException(e);
		}
	}	
}
