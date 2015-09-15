package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.Package;
import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.MessageUtility;

public class AddMessageHandler extends BaseHandler 
{	
	//private AddMessageRequest request = new AddMessageRequest();
	private AddMessageRequest request;
	
	public AddMessageHandler(UploadManager uploadManager, AddMessageRequest request) {
		super(uploadManager);
		
		this.request = request;
	}

	public void makeRequest(String packageId, String keyCode, String message) throws MessageException 
	{	
		request.setPackageId(packageId);
		
		try {
			Package packageInfo = getPackageInfo(packageId);
			String encryptionKey = createEncryptionKey(packageInfo.getServerSecret(), keyCode);
			
			MessageUtility utility = new MessageUtility(super.uploadManager);
			utility.encryptAndUploadMessage(message, encryptionKey, request);
		} catch (IOException e) {
			throw new MessageException(e);
		} catch (SendFailedException e) {
			throw new MessageException(e);
		}
	}
	
	protected Package getPackageInfo(String packageId) throws SendFailedException, IOException, MessageException
	{
		Package info;
		try {
			info = ((PackageInformationHandler)(HandlerFactory
					.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(packageId);
		} catch (PackageInformationFailedException e) {
			throw new MessageException(e);
		}
		
		return info;
	}
	
	protected String createEncryptionKey(String serverSecret, String keyCode)
    {
        return serverSecret + keyCode;
    }
	
}
