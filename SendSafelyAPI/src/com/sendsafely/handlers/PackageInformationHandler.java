package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sendsafely.dto.Confirmation;
import com.sendsafely.dto.File;
import com.sendsafely.dto.PackageInformation;
import com.sendsafely.dto.Recipient;
import com.sendsafely.dto.request.PackageInformationRequest;
import com.sendsafely.dto.response.ConfirmationResponse;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.PackageInformationResponse;
import com.sendsafely.dto.response.RecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class PackageInformationHandler extends BaseHandler 
{	
	
	private PackageInformationRequest request = new PackageInformationRequest();
	
	public PackageInformationHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public PackageInformation makeRequest(String packageId) throws PackageInformationFailedException {
		request.setPackageId(packageId);
		PackageInformationResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return convert(response);
		}
		else
		{
			throw new PackageInformationFailedException(response.getMessage());
		}
	}
	
	protected PackageInformationResponse send() throws PackageInformationFailedException
	{
		try {
			return send(request, new PackageInformationResponse());
		} catch (SendFailedException e) {
			throw new PackageInformationFailedException(e);
		} catch (IOException e) {
			throw new PackageInformationFailedException(e);
		}
	}
	
	protected PackageInformation convert(PackageInformationResponse obj)
	{
		PackageInformation info = new PackageInformation();
		info.setApproverList(obj.getApproverList());
		info.setFiles(convertFiles(obj.getFiles()));
		info.setLife(obj.getLife());
		info.setNeedsApproval(obj.getNeedsApproval());
		info.setPackageCode(obj.getPackageCode());
		info.setPackageId(obj.getPackageId());
		info.setRecipients(convertRecipients(obj.getRecipients()));
		info.setServerSecret(obj.getServerSecret());
		info.setState(obj.getState());
		return info;
	}
	
	protected List<Recipient> convertRecipients(List<RecipientResponse> responses) 
	{
		List<Recipient> retval = new ArrayList<Recipient>(responses.size());
		for (RecipientResponse resp : responses)
		{
			Recipient r = new Recipient();
			r.setEmail(resp.getEmail());
			r.setNeedsApproval(resp.getNeedsApproval());
			r.setRecipientId(resp.getRecipientId());
			r.setConfirmations(createConfirmationList(resp));
			retval.add(r);
		}
		
		return retval;
	}
	
	protected List<File> convertFiles(List<FileResponse> responses) 
	{
		List<File> retval = new ArrayList<File>(responses.size());
		for (FileResponse resp : responses)
		{
			retval.add(createFile(resp));
		}
		
		return retval;
	}
	
	protected File createFile(FileResponse resp)
	{
		File f = new File();
		f.setFileId(resp.getFileId());
		f.setFileName(resp.getFileName());
		f.setFileSize(Long.parseLong(resp.getFileSize()));
		return f;
	}
	
	protected List<Confirmation> createConfirmationList(RecipientResponse rr)
	{
		List<Confirmation> retval = new ArrayList<Confirmation>(rr.getConfirmations().size());
		for(ConfirmationResponse cr : rr.getConfirmations())
		{
			Confirmation c = new Confirmation();
			c.setIpAddress(cr.getIpAddress());
			c.setTimestamp(cr.getTimestamp());
			c.setFile(createFile(cr.getFile()));
			retval.add(c);
		}
		
		return retval;
	}
	
}
