package com.sendsafely.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sendsafely.Package;
import com.sendsafely.dto.request.GetMessageRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.exceptions.DeletePackageException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;

public class GetMessageHandler extends BaseHandler 
{	
	private GetMessageRequest request;
	
	public GetMessageHandler(UploadManager uploadManager, GetMessageRequest request) {
		super(uploadManager);
		
		this.request = request;
	}

	public String makeRequest(String secureLink) throws MessageException 
	{	
		URL url;
		try {
			url = new URL(secureLink);
		} catch (MalformedURLException e) {
			throw new MessageException("Incorrect secure link");
		}
		
		String packageCode = getPackageCode(url);
		String keyCode = getKeyCode(url);
		
		// Get the packageId..
		Package pkg = getPackageInformation(packageCode);
		request.setPackageId(pkg.getPackageId());
		
		// Calculate the checksum.
		request.setChecksum(CryptoUtil.createChecksum(keyCode, packageCode));
		
		// Send
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) {
			throw new MessageException("Server returned error message");
		}
		
		// Decrypt.
		String message = CryptoUtil.decryptMessage(response.getMessage(), createEncryptionKey(pkg.getServerSecret(), keyCode));
		
		return message;
	}
	
	protected Package getPackageInformation(String packageCode) throws MessageException {
		Package info;
		try {
			info = ((PackageInformationHandler)(HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(packageCode);
		} catch (PackageInformationFailedException e) {
			throw new MessageException(e);
		}
		
		return info;
	}
	
	protected BaseResponse send() throws MessageException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new MessageException(e);
		} catch (IOException e) {
			throw new MessageException(e);
		}
	}
	
	protected String createEncryptionKey(String serverSecret, String keyCode)
    {
        return serverSecret + keyCode;
    }
	
	private String getPackageCode(URL url) throws MessageException
	{
		String query = url.getQuery();
		
		// We only expect one parameter in the URL, the package code..
		String[] parameters = query.split("&");
		
		String packageCode = null; 
		for(String param : parameters) {
			// Parse out the key and the value.
			String[] parts = param.split("=");
			if(parts.length < 2) {
				throw new MessageException("Package Code Parameter not found");
			}
			
			String key = parts[0];
			String value = parts[1];
			
			if(parts.length > 2) {
				for(int i = 2; i<parts.length; i++) {
					value += parts[i];
				}
			}
			
			if(key.equals("packageCode")) {
				packageCode = value;
			}
		}
		
		if(packageCode == null) {
			throw new MessageException("Package Code Parameter not found");
		}
		
		return packageCode;
	}
	
	private String getKeyCode(URL url) throws MessageException
	{
		String query = url.toString();
		
		// We only expect one parameter in the URL, the package code..
		String[] parameters = query.split("#");
		
		if(parameters.length != 2) {
			throw new MessageException("Keycode could not be found");
		}
		
		String hash = parameters[1];
		String keyCode = hash.substring(hash.indexOf('=')+1);
				
		return keyCode;
	}
	
}
