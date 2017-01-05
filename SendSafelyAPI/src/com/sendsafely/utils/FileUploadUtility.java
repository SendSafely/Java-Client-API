package com.sendsafely.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.openpgp.PGPException;

import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.UploadFileResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.file.FileManager;
import com.sendsafely.upload.UploadManager;

public class FileUploadUtility 
{
	private final String UPLOAD_TYPE = "JAVA_API";
	private final long SEGMENT_SIZE = 10485760;
    private final long PGP_HEADER_SIZE = 60;
    private final double PGP_CONVERSION = 1.4;

	private int filePart = 1;
	private FileResponse response;
	
	private UploadManager uploadManager;
	
	public FileUploadUtility(UploadManager uploadManager)
	{
		this.uploadManager = uploadManager;
	}
	
	public int calculateParts(FileManager file) throws UploadFileException
	{
		try {
			long filesize = file.length();
			if (filesize == 0)
	        {
	            return 1;
	        }

	        int parts;
	        parts = (int)((filesize + SEGMENT_SIZE - 1) / SEGMENT_SIZE);

	        return parts;
		} catch(IOException e) {
			throw new UploadFileException(e);
		}
	}
	
	public FileResponse getFileObject()
	{
		return this.response;
	}
	
	public long encryptAndUploadFile(String fileId, String encryptionKey, FileManager file, UploadFileRequest request, long offset, Progress progress) throws IOException, UploadFileException, SendFailedException, LimitExceededException
	{
		request = populateRequest(request);
		
		long bytesToRead = calculateBytesToRead(file.length(), offset);
		
		if(bytesToRead == 0) {
			return 0;
		}

        FileManager encryptedFile = encrypt(file, offset, encryptionKey, bytesToRead);
		
        try
        {
    		UploadFileResponse response = upload(encryptedFile, file.getName(), request, progress);
            encryptedFile.remove();
    		this.response = new FileResponse();
    		this.response.setFileId(response.getMessage());
    		this.response.setFileName(file.getName());
    		this.response.setFileSize(file.length());
    		
    		parseResponse(response);
    		
    		filePart++;
    		return bytesToRead;
        }
        finally
        {
        	encryptedFile.remove();
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

	protected UploadFileResponse upload(FileManager encryptedFile, String filename, UploadFileRequest request, Progress progress) throws SendFailedException, IOException
	{
		SendUtil util = new SendUtil(this.uploadManager);
		return util.sendFile(request.getPath(), request, encryptedFile, filename, progress);
	}
	
	protected String encrypt(String message, String encryptionKey)
	{
		return null;
	}
	
	protected FileManager encrypt(FileManager file, long offset, String encryptionKey, long bytesToRead) throws IOException, UploadFileException
	{
		// Create a temp file to store the segment in.
        FileManager encryptedTempFile = file.createTempFile(file.getName() + "-" + offset, "tmp", calculateFileSize(bytesToRead));
        try
        {
    		OutputStream tmpFileOut = encryptedTempFile.getOutputStream();
    		
    		InputStream in = file.getInputStream();
    		in.skip(offset);
    		
    		char[] passPhrase = encryptionKey.toCharArray();
    		
    		try {
    			CryptoUtil.encryptFile(tmpFileOut, in, passPhrase, file.getName(), bytesToRead);
    		} catch (PGPException e)
    		{
    			throw new UploadFileException(e);
    		}
        } 
        catch (Throwable e)
        {
        	encryptedTempFile.remove();
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

    protected long calculateFileSize(long bytesToRead)
    {
        return (long) ((((double)bytesToRead*PGP_CONVERSION)) + PGP_HEADER_SIZE);
    }
}
