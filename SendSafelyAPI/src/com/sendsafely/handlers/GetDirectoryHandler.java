package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sendsafely.Directory;
import com.sendsafely.File;
import com.sendsafely.dto.Subdirectory;
import com.sendsafely.dto.request.GetDirectoryRequest;
import com.sendsafely.dto.response.DirectoryResponse;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.GetDirectoryResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetDirectoryHandler extends BaseHandler {

	private GetDirectoryRequest request;
	
	protected GetDirectoryHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new GetDirectoryRequest(this.uploadManager.getJsonManager());
	}
	
	public Directory makeRequest(String packageId, String directoryId) throws DirectoryOperationFailedException {
		request.setPackageId(packageId);
		request.setDirectoryId(directoryId);
		GetDirectoryResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DirectoryOperationFailedException(response.getMessage());
		}
		return convert(response);
	}
	
	/**
	 * TODO: Determine what "directory" is in the response
	 * @param response
	 * @return
	 */
	private Directory convert(GetDirectoryResponse response) {
		Directory dir = new Directory(response.getDirectoryId(),response.getDirectoryName());
		dir.setSubDirectories(convert(response.getSubDirectories()));
		dir.setFiles(convert(response.getFiles()));
		return dir;
	}

	private List<File> convert(List<FileResponse> files) {
		List<File> fileList = new ArrayList<File>();
		Iterator<FileResponse> i = files.iterator();
		while(i.hasNext()){
			FileResponse resp = i.next();
			File newFile = new File();
			newFile.setFileId(resp.getFileId());
			newFile.setFileName(resp.getFileName());
			newFile.setFileSize(resp.getFileSize());
			newFile.setParts(resp.getParts());
			fileList.add(newFile);
		}
		return fileList;	
	}

	private Collection<Subdirectory> convert(Collection<DirectoryResponse> subDirectories) {
		Collection<Subdirectory> subdirectoryList = new ArrayList<Subdirectory>();
		Iterator<DirectoryResponse> i = subDirectories.iterator();
		while(i.hasNext()){
			DirectoryResponse resp = i.next();
			Subdirectory subdirectory = new Subdirectory();
			subdirectory.setDirectoryCreatedDate(resp.getCreated());
			subdirectory.setDirectoryId(resp.getDirectoryId());
			subdirectory.setDirectoryName(resp.getName());
			subdirectoryList.add(subdirectory);
		}
		return subdirectoryList;
	}

	protected GetDirectoryResponse send() throws DirectoryOperationFailedException 
	{
		try {
			return send(request, new GetDirectoryResponse());
		} catch (IOException | SendFailedException e) {
			throw new DirectoryOperationFailedException(e.getMessage());
		}
	}

}
