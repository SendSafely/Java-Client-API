package com.sendsafely.handlers;

import com.sendsafely.dto.EncryptedKeycode;
import com.sendsafely.dto.request.UploadKeycodeRequest;
import com.sendsafely.dto.response.*;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadKeycodeException;
import com.sendsafely.upload.UploadManager;

import java.io.IOException;
import java.util.List;

public class UploadKeycodesHandler extends BaseHandler
{

	private UploadKeycodeRequest request;

	public UploadKeycodesHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new UploadKeycodeRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId, List<EncryptedKeycode> encryptedKeycodes) throws UploadKeycodeException {

        for(EncryptedKeycode keycode : encryptedKeycodes) {
        	request = new UploadKeycodeRequest(uploadManager.getJsonManager());
            request.setPackageId(packageId);
            request.setPublicKeyId(keycode.getId());
            request.setKeycode(keycode.getKeycode());
            BaseResponse response = send();

            if (response.getResponse() != APIResponse.SUCCESS) {
                throw new UploadKeycodeException(response.getMessage());
            }
        }
	}
	
	protected BaseResponse send() throws UploadKeycodeException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new UploadKeycodeException(e);
		} catch (IOException e) {
			throw new UploadKeycodeException(e);
		}
	}
	
}
