package com.sendsafely.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.openpgp.PGPException;

import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.UploadFileResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.upload.UploadManager;

public class FileUploadUtility 
{
	private final String UPLOAD_TYPE = "JAVA_API";
	private final long SEGMENT_SIZE = 2621440;
	private int filePart = 1;
	private FileResponse response;
	
	private UploadManager uploadManager;
	
	public FileUploadUtility(UploadManager uploadManager)
	{
		this.uploadManager = uploadManager;
	}
	
	public int calculateParts(long filesize)
	{
		if (filesize == 0)
        {
            return 1;
        }

        int parts;
        parts = (int)((filesize + SEGMENT_SIZE - 1) / SEGMENT_SIZE);

        return parts;
	}
	
	public FileResponse getFileObject()
	{
		return this.response;
	}
	
	public long encryptAndUploadFile(String fileId, String encryptionKey, File file, UploadFileRequest request, long offset, Progress progress) throws IOException, UploadFileException, SendFailedException, LimitExceededException
	{
		request = populateRequest(request);
		
		long bytesToRead = calculateBytesToRead(file.length(), offset);
		
		if(bytesToRead == 0) {
			return 0;
		}

		final File encryptedFile = encrypt(file, offset, encryptionKey, bytesToRead);
		try {

			UploadFileResponse response = upload(encryptedFile, file.getName(), request, progress);
			//this.response = response.getFile();
			this.response = new FileResponse();
			this.response.setFileId(response.getMessage());
			this.response.setFileName(file.getName());
			this.response.setFileSize("" + file.length());

			parseResponse(response);

			filePart++;
			return bytesToRead;
		} finally {
			encryptedFile.delete();
		}
	}
	
	protected void parseResponse(UploadFileResponse response) throws UploadFileException, LimitExceededException
	{
		if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		} 
		else if (response.getResponse() != APIResponse.SUCCESS)
		{
			throw new UploadFileException(response.getMessage());
		}
	}
	
	protected UploadFileRequest populateRequest(UploadFileRequest request)
	{
		request.setUploadType(UPLOAD_TYPE);
		request.setFilePart("" + filePart);
		return request;
	}

	protected UploadFileResponse upload(File encryptedFile, String filename, UploadFileRequest request, Progress progress) throws SendFailedException, IOException
	{
		SendUtil util = new SendUtil(this.uploadManager);
		return util.sendFile(request.getPath(), request, encryptedFile, filename, progress);
	}
	
	protected String encrypt(String message, String encryptionKey)
	{
		return null;
	}
	
	protected File encrypt(File file, long offset, String encryptionKey, long bytesToRead) throws IOException, UploadFileException
	{
		// Create a temp file to store the segment in.
		File encryptedTempFile = File.createTempFile(file.getName() + "-" + offset, "tmp");
		try {
			OutputStream tmpFileOut = new FileOutputStream(encryptedTempFile);

			InputStream in = new FileInputStream(file);
			in.skip(offset);

			char[] passPhrase = encryptionKey.toCharArray();

			try {
				CryptoUtil.encryptFile(tmpFileOut, in, passPhrase, file.getName(), bytesToRead);
			} catch (PGPException e) {
				throw new UploadFileException(e);
			}
		} catch (Throwable e){
			encryptedTempFile.delete();
			throw e;
		}
		
		return encryptedTempFile;
	}
	
	protected long calculateBytesToRead(long totalSize, long offset)
	{
		if((offset + SEGMENT_SIZE) < totalSize) 
		{
			return SEGMENT_SIZE;
		}
		else
		{
			return totalSize - offset;
		}
	}
}
