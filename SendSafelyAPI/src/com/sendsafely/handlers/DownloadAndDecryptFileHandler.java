package com.sendsafely.handlers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import com.sendsafely.Directory;
import com.sendsafely.File;
import com.sendsafely.Package;
import com.sendsafely.ProgressInterface;
import com.sendsafely.connection.ConnectionFactory;
import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.GetDownloadUrlsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.progress.DefaultProgress;
import com.sendsafely.upload.UploadFactory;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;
import com.sendsafely.utils.Progress;

public class DownloadAndDecryptFileHandler extends BaseHandler 
{	
	private final int RETRY_ATTEMPTS = 5;
	private final int RETRY_SLEEP_INCREMENT = 3000;
	private Package packageInfo;
	private String fileId;
	private String directoryId;
	private Directory directoryInfo;
	private String password;
	private ProgressInterface progress;
	
	public DownloadAndDecryptFileHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	@Deprecated
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
	
	@Deprecated
	public java.io.File makeRequest(String packageId, String directoryId, String fileId, String keyCode, ProgressInterface progress) throws DownloadFileException, PasswordRequiredException {
		this.fileId = fileId;
		this.directoryId = directoryId; 
		setupProgress(progress);
		
		// Get the file information
		this.packageInfo = getPackageInfo(packageId);
		this.directoryInfo = getDirectoryInfo(packageId, directoryId);
		this.packageInfo.setKeyCode(keyCode);
		
		File fileToDownload = findFile();		
		java.io.File systemFile = getSystemFile(fileToDownload);
		
		// Download the file
		return downloadFile(systemFile, fileToDownload);
	}
	
	@Deprecated
	public java.io.File makeRequest(String packageId, String directoryId, String fileId, String keyCode,
			ProgressInterface progress, String password) throws DownloadFileException, PasswordRequiredException {
		this.fileId = fileId;
		this.directoryId = directoryId; 
		this.password = password;
		setupProgress(progress);
		
		if (directoryId != null)
			this.directoryInfo = getDirectoryInfo(packageId, directoryId);
		
		
		// Get the file information
		this.packageInfo = getPackageInfo(packageId);
		this.packageInfo.setKeyCode(keyCode);
		
		File fileToDownload = findFile();		
		java.io.File systemFile = getSystemFile(fileToDownload);
		
		// Download the file
		return downloadFile(systemFile, fileToDownload);
	}
	
	public java.io.File makeRequestS3(String packageId, String directoryId, String fileId, String keyCode, 
			ProgressInterface progress, String password, boolean forceProxy) throws DownloadFileException, PasswordRequiredException {
		this.fileId = fileId;
		this.directoryId = directoryId; 
		this.password = password;
		setupProgress(progress);
		
		if (directoryId != null)
			this.directoryInfo = getDirectoryInfo(packageId, directoryId);
		
		// Get the file information
		this.packageInfo = getPackageInfo(packageId);
		this.packageInfo.setKeyCode(keyCode);
		
		File fileToDownload = findFile();		
		java.io.File systemFile = getSystemFile(fileToDownload);
		
		// Download the file
		java.io.File file = null;
		try {
			file = downloadFileS3(systemFile, fileToDownload, password, forceProxy, 0);
		} catch (GetDownloadUrlsException e) {
			throw new DownloadFileException(e.getMessage());
		} catch (LimitExceededException e){
			try {
				file = downloadFileS3(systemFile, fileToDownload, password, true, Integer.parseInt(e.getMessage().toString()));
			} catch (GetDownloadUrlsException e1) {
				throw new DownloadFileException(e.getMessage());
			} catch (LimitExceededException e1) {
				throw new DownloadFileException("Unable to get download URLs");
			}
		}catch(Exception e){
			throw new DownloadFileException(e);
		}
		return file;
	}
	
	private Directory getDirectoryInfo(String packageId, String directoryId) throws DownloadFileException {
		
		GetDirectoryHandler handler = ((GetDirectoryHandler)HandlerFactory.getInstance(uploadManager, Endpoint.GET_DIRECTORY));
		try{
			return handler.makeRequest(packageId, directoryId);
		} catch (DirectoryOperationFailedException e){
			throw new DownloadFileException(e);
		}
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
	
	@Deprecated
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
						InputStream stream;
						if(directoryId == null){
							stream = handler.makeRequest(packageInfo, ssFile.getFileId(), part, password);
						}else{
							stream = handler.makeRequest(packageInfo, directoryId, ssFile.getFileId(), part, password);
						}
						
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
	
	private java.io.File downloadFileS3(java.io.File outFile, File ssFile, String password, boolean forceProxy, int filePart) throws DownloadFileException, PasswordRequiredException, GetDownloadUrlsException, LimitExceededException {
		
		Progress progress = new Progress(this.progress, ssFile.getFileId());
		progress.setTotal(ssFile.getFileSize());
		progress.resetCurrent();
		
		Timer timer = startTimer(progress);

		int failCounter = 0;
		OutputStream output = getOutputStream(outFile);
		try{
			int part;
			if(filePart>0){
				part = filePart;
			}else{
				part = 1;
			}
			
			GetDownloadUrlsHandler urlHandler = new GetDownloadUrlsHandler(uploadManager,ssFile.getParts());
			String checksum = calculateChecksum(packageInfo.getPackageCode(), packageInfo.getKeyCode());
			urlHandler.fetchDownloadUrls(packageInfo.getPackageId(), ssFile.getFileId(), this.directoryId, part, forceProxy, password, checksum);
			
			while (!urlHandler.getCurrentDownloadUrls().isEmpty()) {
				
				String downloadUrl = urlHandler.getCurrentDownloadUrls().get(0).get("url");
				//This is the fail/retry loop (on any exception)
				for (failCounter = 0; failCounter <= RETRY_ATTEMPTS; failCounter++)
				{
					try
					{
						InputStream stream;
						if(forceProxy){
							DownloadFileHandler proxyHandler = new DownloadFileHandler(uploadManager);
							stream = proxyHandler.makeRequest(downloadUrl, packageInfo, part, password);
							
						}else{
							URL u = new URL(downloadUrl);
							ConnectionManager connection = ConnectionFactory.getDefaultManager(u.getHost());
							DownloadFileHandler s3Handler = new DownloadFileHandler(UploadFactory.getManagerS3(connection));
							stream = s3Handler.makeRequest(downloadUrl);
						}
						decrypt(stream, output, progress);
						break;
					}
					catch(DownloadFileException e)
					{
						if(failCounter == RETRY_ATTEMPTS && !forceProxy){
							//limit exceeded, caller should catch this exception and try again.
							throw new LimitExceededException(Integer.toString(part));
						}
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
					} catch (MalformedURLException e) {
						throw new DownloadFileException(e);
					}
				}
				urlHandler.removeUrl();
				part++;
				urlHandler.fetchDownloadUrls(packageInfo.getPackageId(), ssFile.getFileId(), this.directoryId, part, forceProxy, password, checksum);
			}
		}catch(LimitExceededException e){
			throw new LimitExceededException(e.getMessage());
		}
		finally
		{
			closeStream(output);
			progress.finished();
			stopTimer(timer);
		}
		return outFile;
	}
	
	private String calculateChecksum(String packageCode, String keycode)
	{
		return CryptoUtil.createChecksum(keycode, packageCode);
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
	
	private java.io.File createTempFile(File file) throws DownloadFileException {
		String filename = file.getFileName().replaceAll("[<>:\"/\\\\|?*]", "_");
		try {
			return java.io.File.createTempFile(filename, "");
		} catch (IOException e) {
			throw new DownloadFileException(e);
		}
	}
	
	private File findFile() throws DownloadFileException
	{
		if(this.directoryId!= null){
			for(File f : directoryInfo.getFiles()) {
				if(f.getFileId().equals(fileId)) {
					return new File(f.getFileId(), f.getFileName(), f.getFileSize(), f.getParts());
				}
			}
		}else{
			for(File f : packageInfo.getFiles()) {
				if(f.getFileId().equals(fileId)) {
					return f;
				}
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
