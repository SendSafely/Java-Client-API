package com.sendsafely.handlers;

import java.io.IOException;
import java.io.InputStream;

import com.sendsafely.Package;
import com.sendsafely.dto.request.DownloadFileFromDirectoryRequest;
import com.sendsafely.dto.request.DownloadFileRequest;
import com.sendsafely.dto.response.DownloadFileResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;

public class DownloadFileHandler extends BaseHandler 
{	
	private final String DOWNLOAD_API = "JAVA_API";
	private Package packageInfo;
	private String fileId;
	private String directoryId;
	private String checksum;
	private String password;
	private int part;
	private String path;
	
	public DownloadFileHandler(UploadManager uploadManager) {
		super(uploadManager);
	}
	
	public InputStream makeRequest(String url) throws DownloadFileException {
		DownloadFileResponse response = downloadFile(new DownloadFileRequest(uploadManager.getJsonManager(), HTTPMethod.GET, url));
		if(response.getResponse() == APIResponse.SUCCESS) {
			return response.getFileStream();
		} else {
			throw new DownloadFileException("Server returned error message: " + response.getResponse() + " " + response.getMessage());
		}
	}
	
	public InputStream makeRequest(String path, Package packageInfo, int part, String password) throws DownloadFileException, PasswordRequiredException {
		this.path = path;
		this.packageInfo = packageInfo;
		this.part = part;
		this.password = password;
		
		return downloadFile();
	}
	
	@Deprecated
	public InputStream makeRequest(Package packageInfo, String fileId, int part, String password) throws DownloadFileException, PasswordRequiredException {
		this.fileId = fileId;
		this.packageInfo = packageInfo;
		this.part = part;
		this.password = password;
		
		return downloadFile();
	}
	
	@Deprecated
	public InputStream makeRequest(Package packageInfo, String directoryId, String fileId, int part, String password) throws DownloadFileException, PasswordRequiredException {
		this.fileId = fileId;
		this.directoryId = directoryId;
		this.packageInfo = packageInfo;
		this.part = part;
		this.password = password;
		if(directoryId == null){
			return downloadFile();
		}else{
			return downloadFile(directoryId);
		}
	}
	
	private InputStream downloadFile() throws DownloadFileException, PasswordRequiredException {
		this.checksum = calculateChecksum(packageInfo.getPackageCode(), packageInfo.getKeyCode());
		DownloadFileRequest request = createRequest(part);
		DownloadFileResponse response = downloadFile(request);
		if(response.getResponse() == APIResponse.SUCCESS) {
			return response.getFileStream();
		} else if(response.getResponse() == APIResponse.PASSWORD_REQUIRED) {
			throw new PasswordRequiredException();
		} else {
			throw new DownloadFileException("Server returned error message: " + response.getResponse() + " " + response.getMessage());
		}
	}
	
	@Deprecated
	private InputStream downloadFile(String directoryId) throws DownloadFileException, PasswordRequiredException {
		this.checksum = calculateChecksum(packageInfo.getPackageCode(), packageInfo.getKeyCode());
		DownloadFileFromDirectoryRequest request = createRequest(directoryId, part);
		DownloadFileResponse response = downloadFile(request);
		if(response.getResponse() == APIResponse.SUCCESS) {
			return response.getFileStream();
		} else if(response.getResponse() == APIResponse.PASSWORD_REQUIRED) {
			throw new PasswordRequiredException();
		} else {
			throw new DownloadFileException("Server returned error message: " + response.getResponse() + " " + response.getMessage());
		}
	}
	
	private DownloadFileRequest createRequest(int part)
	{
		DownloadFileRequest request = new DownloadFileRequest(uploadManager.getJsonManager());
		if (path != null) {
			request = new DownloadFileRequest(uploadManager.getJsonManager(),HTTPMethod.POST, path);
		} else {
			request.setPackageId(packageInfo.getPackageId());
			request.setFileId(fileId);
		}
		
		request.setChecksum(checksum);
		request.setPart(part);	
		request.setApi(DOWNLOAD_API);
		
		if(password != null) {
			request.setPassword(password);
		}
		
		return request;
	}
	
	@Deprecated 
	private DownloadFileFromDirectoryRequest createRequest(String directoryId, int part)
	{
		DownloadFileFromDirectoryRequest request = new DownloadFileFromDirectoryRequest(uploadManager.getJsonManager());
		request.setPackageId(packageInfo.getPackageId());
		request.setFileId(fileId);
		request.setDirectoryId(directoryId);
		request.setChecksum(checksum);
		request.setPart(part);	
		request.setApi(DOWNLOAD_API);
		
		if(password != null) {
			request.setPassword(password);
		}
		
		return request;
	}
	
	private DownloadFileResponse downloadFile(DownloadFileRequest request) throws DownloadFileException
	{
		try {
			return send(request, new DownloadFileResponse());
		} catch (SendFailedException e) {
			throw new DownloadFileException(e);
		} catch (IOException e) {
			throw new DownloadFileException(e);
		}
	}
	
	@Deprecated
	private DownloadFileResponse downloadFile(DownloadFileFromDirectoryRequest request) throws DownloadFileException
	{
		try {
			return send(request, new DownloadFileResponse());
		} catch (SendFailedException e) {
			throw new DownloadFileException(e);
		} catch (IOException e) {
			throw new DownloadFileException(e);
		}
	}
	
	private String calculateChecksum(String packageCode, String keycode)
	{
		return CryptoUtil.createChecksum(keycode, packageCode);
	}	
}
