package com.sendsafely.handlers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;

import com.sendsafely.File;
import com.sendsafely.ProgressInterface;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.progress.DefaultProgress;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.Progress;
import com.sendsafely.Package;

public class DownloadAndDecryptFileHandler extends BaseHandler 
{	
	private final int RETRY_ATTEMPTS = 10;
	private final int RETRY_SLEEP_INCREMENT = 5000;
	private Package packageInfo;
	private String fileId;
	private String password;
	private ProgressInterface progress;
	
	public DownloadAndDecryptFileHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public java.io.File makeRequest(String packageId, String fileId, String keyCode, ProgressInterface progress, String password) throws DownloadFileException, PasswordRequiredException {
		this.fileId = fileId;
		this.password = password;
		setupProgress(progress);
		
		// Get the file information
		this.packageInfo = getPackageInfo(packageId);
		this.packageInfo.setKeyCode(keyCode);
		
		File fileToDownload = findFile();		
		java.io.File systemFile = getSystemFile(fileToDownload);
		
		// Download the file
		return downloadFile(systemFile, fileToDownload);
	}
	
	private java.io.File getSystemFile(File ssFile) throws DownloadFileException {
		java.io.File systemFile = createTempFile(ssFile);
		return systemFile;
	}
	
	private void setupProgress(ProgressInterface progress)
	{
		if(progress == null) {
			this.progress = new DefaultProgress();
		} else {
			this.progress = progress;
		}
	}
	
	private Package getPackageInfo(String packageId) throws DownloadFileException
	{
		PackageInformationHandler handler = new PackageInformationHandler(uploadManager);
		try {
			return handler.makeRequest(packageId);
		} catch (PackageInformationFailedException e) {
			throw new DownloadFileException(e);
		}
	}
	
	private java.io.File downloadFile(java.io.File outFile, File ssFile) throws DownloadFileException, PasswordRequiredException {
		
		Progress progress = new Progress(this.progress, ssFile.getFileId());
		progress.setTotal(ssFile.getFileSize());
		progress.resetCurrent();
		
		Timer timer = startTimer(progress);

		int failCounter = 0;
		OutputStream output = getOutputStream(outFile);
		try{
			for(int part = 1; part<=ssFile.getParts(); part++) {
				
				//This is the fail/retry loop (on any exception)
				for (failCounter = 0; failCounter <= RETRY_ATTEMPTS; failCounter++)
				{
					try
					{
						DownloadFileHandler handler = new DownloadFileHandler(uploadManager);
						InputStream stream = handler.makeRequest(packageInfo, ssFile.getFileId(), part, password);
						decrypt(stream, output, progress);
						break;
					}
					catch(DownloadFileException e)
					{
						if(failCounter == RETRY_ATTEMPTS)
						{
							outFile.delete();
							throw new DownloadFileException(e);
						}
						
						//Download failed...retrying in sleepInterval milliseconds (increases on each failure)
						int sleepInterval = failCounter * RETRY_SLEEP_INCREMENT;

						try 
						{
							Thread.sleep(sleepInterval);
						} 
						catch (InterruptedException e1) 
						{
							throw new DownloadFileException(e);
						}
					}
				}
			}
		}
		finally
		{
			closeStream(output);
			progress.finished();
			stopTimer(timer);
		}
		return outFile;
	}
	
	private void stopTimer(Timer timer)
	{
		timer.cancel();
	}
	
	private void closeStream(OutputStream output) throws DownloadFileException
	{
		try {
			output.close();
		} catch (IOException e) {
			throw new DownloadFileException(e);
		}
	}
	
	private OutputStream getOutputStream(java.io.File newFile) throws DownloadFileException {
		try {
			return new FileOutputStream(newFile);
		} catch (FileNotFoundException e) {
			throw new DownloadFileException(e);
		}
	}
	
	private java.io.File createTempFile(File file) throws DownloadFileException
	{
		try {
			return java.io.File.createTempFile(file.getFileName(), "");
		} catch (IOException e) {
			throw new DownloadFileException(e);
		}
	}
	
	private File findFile() throws DownloadFileException
	{
		for(File f : packageInfo.getFiles()) {
			if(f.getFileId().equals(fileId)) {
				return f;
			}
		}
		throw new DownloadFileException("Failed to find the file");
	}
	
	private void decrypt(InputStream stream, OutputStream fileStream, Progress progress) throws DownloadFileException {
		new DecryptFileHandler(uploadManager, progress).execute(fileStream, stream, packageInfo);
	}
	
	private Timer startTimer(Progress progress)
	{
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(progress, 1000, 1000);
		return timer;
	}
}
