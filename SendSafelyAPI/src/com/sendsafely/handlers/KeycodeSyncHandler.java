package com.sendsafely.handlers;

import java.util.List;

import com.sendsafely.dto.EncryptedKeycode;
import com.sendsafely.dto.PublicKey;
import com.sendsafely.exceptions.PublicKeyEncryptionFailedException;
import com.sendsafely.exceptions.PublicKeysFailedException;
import com.sendsafely.exceptions.UploadKeycodeException;
import com.sendsafely.upload.UploadManager;

public class KeycodeSyncHandler extends BaseHandler
{
	public KeycodeSyncHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public void syncPackageKeycode(String packageId, String keyCode) throws UploadKeycodeException {
		uploadKeycodes(packageId, getPublicKeys(packageId), keyCode);
	}

	public static boolean shouldSyncRecipient(String recipientId, Boolean checkForPublicKeys) {
		return recipientId != null && !Boolean.FALSE.equals(checkForPublicKeys);
	}

	// The flag is deliberately not consulted: a duplicate response always carries false, so
	// gating on it would mean a re-add never repairs a missing key.
	public static boolean shouldSyncExistingRecipient(String recipientId) {
		return recipientId != null;
	}

	public void syncRecipientKeycode(String packageId, String recipientId, String keyCode) throws UploadKeycodeException {
		uploadKeycodes(packageId, getPublicKeys(packageId, recipientId), keyCode);
	}

	protected void uploadKeycodes(String packageId, List<PublicKey> publicKeys, String keyCode) throws UploadKeycodeException {
		if (publicKeys == null || publicKeys.isEmpty()) {
			// Not an error: the recipient-scoped endpoint returns an empty list when a recipient
			// has no trusted devices.
			return;
		}

		new UploadKeycodesHandler(this.uploadManager).makeRequest(packageId, encrypt(publicKeys, keyCode));
	}

	protected List<PublicKey> getPublicKeys(String packageId) throws UploadKeycodeException {
		try {
			return new GetPublicKeysHandler(this.uploadManager).makeRequest(packageId);
		} catch (PublicKeysFailedException e) {
			throw new UploadKeycodeException(e);
		}
	}

	protected List<PublicKey> getPublicKeys(String packageId, String recipientId) throws UploadKeycodeException {
		try {
			return new GetPublicKeysHandler(this.uploadManager).makeRequest(packageId, recipientId);
		} catch (PublicKeysFailedException e) {
			throw new UploadKeycodeException(e);
		}
	}

	protected List<EncryptedKeycode> encrypt(List<PublicKey> publicKeys, String keyCode) throws UploadKeycodeException {
		try {
			return new EncryptKeycodeHandler(this.uploadManager).encrypt(publicKeys, keyCode);
		} catch (PublicKeyEncryptionFailedException e) {
			throw new UploadKeycodeException(e);
		}
	}

}
