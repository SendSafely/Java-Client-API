package com.sendsafely.handlers;

import com.sendsafely.Package;
import com.sendsafely.ProgressInterface;
import com.sendsafely.dto.EncryptedKeycode;
import com.sendsafely.dto.PublicKey;
import com.sendsafely.dto.request.CreateFileIdRequest;
import com.sendsafely.dto.response.CreateFileIdResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.exceptions.*;
import com.sendsafely.file.FileManager;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;
import com.sendsafely.utils.FileUploadUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EncryptKeycodeHandler extends BaseHandler
{

	public EncryptKeycodeHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public List<EncryptedKeycode> encrypt(List<PublicKey> publicKeys, String keyCode) throws PublicKeyEncryptionFailedException {
        List<EncryptedKeycode> encryptedKeycodes = new ArrayList<EncryptedKeycode>(publicKeys.size());
        for(PublicKey publicKey : publicKeys) {
            encryptedKeycodes.add(create(publicKey, CryptoUtil.encryptKeycode(publicKey.getKey(), keyCode)));
        }

        return encryptedKeycodes;
	}

    protected EncryptedKeycode create(PublicKey publicKey, String keycode)
    {
        EncryptedKeycode encryptedKeycode = new EncryptedKeycode();
        encryptedKeycode.setId(publicKey.getId());
        encryptedKeycode.setKeycode(keycode);

        return encryptedKeycode;
    }

}
