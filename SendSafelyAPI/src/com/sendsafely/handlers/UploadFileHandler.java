package com.sendsafely.handlers;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

import com.sendsafely.ProgressInterface;
import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.FileUploadUtility;
import com.sendsafely.utils.Progress;

public class UploadFileHandler extends BaseHandler 
{	
	private final int RETRY_ATTEMPTS = 3;
	private UploadFileRequest request = new UploadFileRequest();
	
	public UploadFileHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public void makeRequest(String packageId, String fileId, String encryptionKey, File file, ProgressInterface progressCallback) throws SendFailedException, IOException, LimitExceededException, UploadFileException 
	{	
		upload(file, encryptionKey, packageId, fileId, progressCallback);
	}
	
	protected void upload(File file, String passPhrase, String packageId, String fileId, ProgressInterface progressCallback) throws UploadFileException, LimitExceededException
	{
		request.setFileId(fileId);
		request.setPackageId(packageId);
		
		FileUploadUtility uploadUtility = new FileUploadUtility(super.uploadManager);
	
		Progress progress = new Progress(progressCallback);
		progress.setTotal(file.length());
		progress.resetCurrent();
		
		Timer timer = startTimer(progress);
		
		long totalBytesRead = 0;
		long bytesRead = 0;
		int failCounter = 0;
		
		try {
			do
			{
				try
				{
					bytesRead = uploadSegment(uploadUtility, fileId, passPhrase, file, totalBytesRead, progress);
					totalBytesRead += bytesRead;
					failCounter = 0;
				}
				catch (SendFailedException e)
				{
					if(failCounter < RETRY_ATTEMPTS)
					{
						failCounter++;
					}
					else
					{
						throw new UploadFileException(e);
					}
				}
			} while (bytesRead > 0);
		} finally 
		{
			stopTimer(timer);
		}
		
	}
	
	protected long uploadSegment(FileUploadUtility uploadUtility, String fileId, String passPhrase, File file, long totalBytesRead, Progress progress) throws UploadFileException, SendFailedException, LimitExceededException
	{
		try {
			return uploadUtility.encryptAndUploadFile(fileId, passPhrase, file, request, totalBytesRead, progress);
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
	}
	
	protected Timer startTimer(Progress progress)
	{
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(progress, 1000, 1000);
		return timer;
	}
	
	protected void stopTimer(Timer timer)
	{
		timer.cancel();
	}
	
}
