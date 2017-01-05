package com.sendsafely.handlers;

import com.sendsafely.dto.request.GetKeycodeRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.GetKeycodeFailedException;
import com.sendsafely.exceptions.PublicKeyDecryptionFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;

import java.io.IOException;
import java.security.NoSuchProviderException;

public class GetKeycode extends BaseHandler
{

	private GetKeycodeRequest request;

	public GetKeycode(UploadManager uploadManager) {
		super(uploadManager);
        request = new GetKeycodeRequest(uploadManager.getJsonManager());
	}

	public String get(String packageId, String privateKey, String publicKeyId) throws GetKeycodeFailedException {
		
		request.setPackageId(packageId);
        request.setPublicKeyId(publicKeyId);
		BaseResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
            return decrypt(privateKey, response.getMessage());
		}
		else
		{
			throw new GetKeycodeFailedException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws GetKeycodeFailedException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new GetKeycodeFailedException(e);
		} catch (IOException e) {
			throw new GetKeycodeFailedException(e);
		}
	}

    protected String decrypt(String privateKey, String encryptedKeycode) throws GetKeycodeFailedException {
        try {
            return CryptoUtil.decryptKeycode(privateKey, encryptedKeycode);
        } catch (IOException e) {
            throw new GetKeycodeFailedException(e);
        } catch (NoSuchProviderException e) {
            throw new GetKeycodeFailedException(e);
        } catch (PublicKeyDecryptionFailedException e) {
        	throw new GetKeycodeFailedException(e);
		}
    }
	
}
