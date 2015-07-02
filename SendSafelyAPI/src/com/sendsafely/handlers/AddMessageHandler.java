package com.sendsafely.handlers;

import java.io.IOException;
import java.util.Timer;

import com.sendsafely.dto.PackageInformation;
import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.MessageUtility;
import com.sendsafely.utils.Progress;
import com.sendsafely.utils.SendSafelyConfig;

public class AddMessageHandler extends BaseHandler 
{	
	private AddMessageRequest request = new AddMessageRequest();
	
	public AddMessageHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public void makeRequest(String packageId, String keyCode, String message) throws MessageException 
	{	
		request.setPackageId(packageId);
		
		try {
			PackageInformation packageInfo = getPackageInfo(packageId);
			String encryptionKey = createEncryptionKey(packageInfo.getServerSecret(), keyCode);
			
			MessageUtility utility = new MessageUtility(super.uploadManager);
			utility.encryptAndUploadMessage(message, encryptionKey, request);
		} catch (IOException e) {
			throw new MessageException(e);
		} catch (SendFailedException e) {
			throw new MessageException(e);
		}
	}
	
	protected PackageInformation getPackageInfo(String packageId) throws SendFailedException, IOException, MessageException
	{
		PackageInformation info;
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
