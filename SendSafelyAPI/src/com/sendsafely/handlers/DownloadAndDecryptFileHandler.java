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
import com.sendsafely.progress.DefaultProgress;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.Progress;
import com.sendsafely.Package;

public class DownloadAndDecryptFileHandler extends BaseHandler 
{	
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
		
		// Download the file
		return downloadFile();
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
	
	private java.io.File downloadFile() throws DownloadFileException, PasswordRequiredException {
		
		File fileToDownload = findFile();
		
		Progress progress = new Progress(this.progress);
		progress.setTotal(fileToDownload.getFileSize());
		progress.resetCurrent();
		
		Timer timer = startTimer(progress);
		
		java.io.File newFile = createTempFile(fileToDownload);
		OutputStream output = getOutputStream(newFile);
		for(int part = 1; part<=fileToDownload.getParts(); part++) {
			DownloadFileHandler handler = new DownloadFileHandler(uploadManager);
			InputStream stream = handler.makeRequest(packageInfo, fileToDownload.getFileId(), part, password);
			decrypt(stream, output, progress);
		}
		closeStream(output);
		stopTimer(timer);
		
		return newFile;
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
