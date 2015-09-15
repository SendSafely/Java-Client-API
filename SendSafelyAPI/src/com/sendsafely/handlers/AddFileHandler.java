package com.sendsafely.handlers;

import java.io.File;
import java.io.IOException;

import com.sendsafely.Package;
import com.sendsafely.ProgressInterface;
import com.sendsafely.Recipient;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.request.CreateFileIdRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.dto.response.CreateFileIdResponse;
import com.sendsafely.dto.response.CreatePackageResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.FileUploadUtility;
import com.sendsafely.utils.SendSafelyConfig;

public class AddFileHandler extends BaseHandler 
{	
	
	private CreateFileIdRequest request = new CreateFileIdRequest();
	
	public AddFileHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public com.sendsafely.File makeRequest(String packageId, String keyCode, File file) throws LimitExceededException, UploadFileException 
	{	
		return makeRequest(packageId, keyCode, file, null);
	}
	
	public com.sendsafely.File makeRequest(String packageId, String keyCode, File file, ProgressInterface progress) throws LimitExceededException, UploadFileException 
	{	
		FileUploadUtility uploadUtility = new FileUploadUtility(super.uploadManager);
		String fileId = createFileId(file, packageId, uploadUtility.calculateParts(file.length()));
		
		Package packageInfo = getPackageInfo(packageId);
		
		String encryptionKey = createEncryptionKey(packageInfo.getServerSecret(), keyCode);
		
		UploadFileHandler handler = (UploadFileHandler)HandlerFactory.getInstance(super.uploadManager, Endpoint.UPLOAD_FILE);
		com.sendsafely.File metaFile = makeRequest(handler, packageId, fileId, encryptionKey, file, progress);
		
		return metaFile;
	}
	
	protected com.sendsafely.File makeRequest(UploadFileHandler handler, String packageId, String fileId, String encryptionKey, File file, ProgressInterface progress) throws UploadFileException, LimitExceededException
	{
		try {
			return handler.makeRequest(packageId, fileId, encryptionKey, file, progress);
		} catch (SendFailedException e) {
			throw new UploadFileException(e);
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
	}
	
	protected Package getPackageInfo(String packageId) throws UploadFileException
	{
		Package info;
		try {
			info = ((PackageInformationHandler)(HandlerFactory
					.getInstance(super.uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(packageId);
		} catch (PackageInformationFailedException e) {
			throw new UploadFileException(e);
		}
		
		return info;
	}
	
	protected String createEncryptionKey(String serverSecret, String keyCode)
    {
        return serverSecret + keyCode;
    }
	
	protected String createFileId(File file, String packageId, int parts) throws LimitExceededException, UploadFileException
	{
		request.setFilename(file.getName());
		request.setFilesize(file.length());
		request.setParts(parts);
		request.setPackageId(packageId);
		
		CreateFileIdResponse response = send();
		
		if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		}
		else if (response.getResponse() != APIResponse.SUCCESS)
		{
			throw new UploadFileException(response.getMessage());
		}
		
		return response.getMessage();
	}
	
	protected CreateFileIdResponse send() throws UploadFileException
	{
		try {
			return send(request, new CreateFileIdResponse());
		} catch (SendFailedException e) {
			throw new UploadFileException(e);
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
	}
}
