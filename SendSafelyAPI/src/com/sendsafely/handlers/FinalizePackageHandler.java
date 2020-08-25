package com.sendsafely.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.sendsafely.Package;
import com.sendsafely.dto.EncryptedKeycode;
import com.sendsafely.dto.PackageURL;
import com.sendsafely.dto.PublicKey;
import com.sendsafely.dto.request.FinalizePackageRequest;
import com.sendsafely.dto.response.CreatePackageResponse;
import com.sendsafely.dto.response.FinalizePackageResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.exceptions.*;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;

public class FinalizePackageHandler extends BaseHandler 
{	
	
	private FinalizePackageRequest request;
	private boolean notify = false;
	
	public FinalizePackageHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new FinalizePackageRequest(uploadManager.getJsonManager());
	}

	public PackageURL makeRequest(String packageId, String keyCode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		// Get the public keys available for the users.
        List<PublicKey> publicKeys = getPublicKeys(packageId);

        // Encrypt the keycode with the public keys.
        List<EncryptedKeycode> encryptedKeycodes = encryptKeycode(publicKeys, keyCode);

        // Upload the keycodes
        uploadKeycodes(packageId, encryptedKeycodes);

        // Finalize
        request.setPackageId(packageId);
		
		Package info;
		try {
			info = ((PackageInformationHandler)(HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(packageId);
		} catch (PackageInformationFailedException e) {
			throw new FinalizePackageFailedException(e);
		}
		
		return makeRequest(packageId, info.getPackageCode(), keyCode);
	}
	
	public PackageURL makeRequest(String packageId, String keyCode, boolean undisclosedRecipients, String password) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		
		if(password != null) {
			request.setPassword(password);
		}
		request.setUndisclosedRecipients(undisclosedRecipients);
		
		return makeRequest(packageId, keyCode);
	}
	
	public PackageURL makeRequest(String packageId, String keyCode, boolean undisclosedRecipients) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		
		request.setUndisclosedRecipients(undisclosedRecipients);
		
		return makeRequest(packageId, keyCode);
	}

	protected PackageURL makeRequest(String packageId, String packageCode, String keyCode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		request.setPackageId(packageId);
		
		request.setChecksum(CryptoUtil.createChecksum(keyCode, packageCode));
		FinalizePackageResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS || response.getResponse() == APIResponse.PACKAGE_NEEDS_APPROVAL) 
		{
			PackageURL packageUrl = convert(response, keyCode);
			
			if (notify) {
				try {
					notifyPackageRecipients(packageId,keyCode);
					packageUrl.setNotificationStatus(APIResponse.SUCCESS.toString());
				} catch (NotifyPackageRecipientsException e) {
					packageUrl.setNotificationStatus(e.getMessage());
				}
			}
			
			return packageUrl;
		}
		else if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		}
		else if(response.getResponse() == APIResponse.APPROVER_REQUIRED)
		{
			throw new ApproverRequiredException(response.getMessage());
		}
		else
		{
			throw new FinalizePackageFailedException(response.getMessage(), response.getErrors());
		}
	}
	
    protected void uploadKeycodes(String packageId, List<EncryptedKeycode> encryptedKeycodes) throws FinalizePackageFailedException {
        UploadKeycodesHandler handler = new UploadKeycodesHandler(this.uploadManager);
        try {
            handler.makeRequest(packageId, encryptedKeycodes);
        } catch (UploadKeycodeException e) {
            throw new FinalizePackageFailedException(e);
        }
    }

    protected List<PublicKey> getPublicKeys(String packageId) throws FinalizePackageFailedException {
        GetPublicKeysHandler handler = new GetPublicKeysHandler(this.uploadManager);
        try {
            return handler.makeRequest(packageId);
        } catch (PublicKeysFailedException e) {
            throw new FinalizePackageFailedException(e);
        }
    }

    protected List<EncryptedKeycode> encryptKeycode(List<PublicKey> publicKeys, String keyCode) throws FinalizePackageFailedException {

        EncryptKeycodeHandler handler = new EncryptKeycodeHandler(uploadManager);

        try {
            return handler.encrypt(publicKeys, keyCode);
        } catch (PublicKeyEncryptionFailedException e) {
            throw new FinalizePackageFailedException(e);
        }
    }

	protected PackageURL convert(FinalizePackageResponse response, String keyCode) throws FinalizePackageFailedException
	{
		try {
			PackageURL pUrl = new PackageURL();
			pUrl.setSecureLink(new URL(response.getMessage() + "#keyCode=" + keyCode));
			pUrl.setKeycode(keyCode);
			pUrl.setNeedsApproval(response.getResponse() == APIResponse.PACKAGE_NEEDS_APPROVAL);
			return pUrl;
		} catch (MalformedURLException e) {
			throw new FinalizePackageFailedException(e);
		}
	}
	
	protected FinalizePackageResponse send() throws FinalizePackageFailedException
	{
		try {
			return send(request, new FinalizePackageResponse());
		} catch (SendFailedException e) {
			throw new FinalizePackageFailedException(e);
		} catch (IOException e) {
			throw new FinalizePackageFailedException(e);
		}
	}
	
	protected Package convert(CreatePackageResponse obj)
	{
		Package info = new Package();
		info.setPackageCode(obj.getPackageCode());
		info.setPackageId(obj.getPackageId());
		info.setServerSecret(obj.getServerSecret());
		return info;
	}

	protected void notifyPackageRecipients(String packageId, String keycode) throws NotifyPackageRecipientsException {

		NotifyPackageRecipientsHandler handler = new NotifyPackageRecipientsHandler(uploadManager,keycode);
		
		try {
            handler.makeRequest(packageId);
        } catch (NotifyPackageRecipientsException e) {
            throw new NotifyPackageRecipientsException(e);
        }
	}
	
	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}
	
	public FinalizePackageHandler setRequestAllowReplyAll(boolean allowReplyAll) {
		request.setAllowReplyAll(allowReplyAll);
		return this;
	}
	
}
