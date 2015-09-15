package com.sendsafely.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.openpgp.PGPException;

import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;
import com.sendsafely.utils.Progress;
import com.sendsafely.Package;

public class DecryptFileHandler extends BaseHandler 
{	
	private Progress progress;
	
	public DecryptFileHandler(UploadManager uploadManager, Progress progress) {
		super(uploadManager);
		
		this.progress = progress;
	}

	public void execute(OutputStream output, InputStream input, Package packageInformation) throws DownloadFileException {
		String decryptionKey = generateDecryptionKey(packageInformation.getServerSecret(), packageInformation.getKeyCode());
		decrypt(input, output, decryptionKey);
	}
	
	private String generateDecryptionKey(String serverSecret, String keycode)
	{
		return serverSecret + keycode;
	}
	
	private void decrypt(InputStream stream, OutputStream fileStream, String decryptionKey) throws DownloadFileException {
		try {
			CryptoUtil.decryptFile(stream, fileStream, decryptionKey, progress);
		} catch (IOException e) {
			throw new DownloadFileException(e);
		} catch (PGPException e) {
			throw new DownloadFileException(e);
		}
	}
}
