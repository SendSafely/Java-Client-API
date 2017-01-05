package com.sendsafely.handlers;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

import com.sendsafely.ProgressInterface;
import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.file.FileManager;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.FileUploadUtility;
import com.sendsafely.utils.Progress;

public class UploadFileHandler extends BaseHandler 
{	
	private final int RETRY_ATTEMPTS = 10;
	private final int RETRY_SLEEP_INCREMENT = 5000;
	private UploadFileRequest request;
	
	public UploadFileHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new UploadFileRequest(uploadManager.getJsonManager());
	}

	public com.sendsafely.File makeRequest(String packageId, String fileId, String encryptionKey, FileManager file, ProgressInterface progressCallback) throws SendFailedException, IOException, LimitExceededException, UploadFileException 
	{	
		return upload(file, encryptionKey, packageId, fileId, progressCallback);
	}
	
	protected com.sendsafely.File upload(FileManager file, String passPhrase, String packageId, String fileId, ProgressInterface progressCallback) throws UploadFileException, LimitExceededException
	{
		request.setFileId(fileId);
		request.setPackageId(packageId);
		
		FileUploadUtility uploadUtility = new FileUploadUtility(super.uploadManager);
	
		Progress progress = new Progress(progressCallback, fileId);
        try {
            progress.setTotal(file.length());
        } catch (IOException e) {
            throw new UploadFileException(e);
        }
        progress.resetCurrent();
		
		Timer timer = startTimer(progress);
		
		long totalBytesRead = 0;
		long bytesRead = 0;
		int failCounter = 0;
		
		try {
			do
			{
				//This is the fail/retry loop (on any exception)
				for (failCounter = 0; failCounter <= RETRY_ATTEMPTS; failCounter++)
				{
					try
					{
						bytesRead = uploadSegment(uploadUtility, fileId, passPhrase, file, totalBytesRead, progress);
						totalBytesRead += bytesRead;
						break;
					}
					catch (Exception e)
					{
						if(failCounter == RETRY_ATTEMPTS)
						{
							throw new UploadFileException(e);
						}
						
						//Upload failed...retrying in sleepInterval milliseconds (increases on each failure)
						int sleepInterval = failCounter * RETRY_SLEEP_INCREMENT;

						try 
						{
							Thread.sleep(sleepInterval);
						} catch (InterruptedException e1) {
							throw new UploadFileException(e);
						}
					}
				}

			} while (bytesRead > 0);
		} finally 
		{
			stopTimer(timer);
		}
		
		return convert(uploadUtility.getFileObject());
	}
	
	protected com.sendsafely.File convert(FileResponse response)
	{
		com.sendsafely.File file = new com.sendsafely.File();		
		//file.setCreatedBy(response.getCreatedByEmail());
		file.setFileId(response.getFileId());
		file.setFileName(response.getFileName());
		file.setFileSize(response.getFileSize());
		return file;
	}
	
	protected long uploadSegment(FileUploadUtility uploadUtility, String fileId, String passPhrase, FileManager file, long totalBytesRead, Progress progress) throws UploadFileException, SendFailedException, LimitExceededException
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
