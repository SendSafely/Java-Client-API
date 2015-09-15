package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sendsafely.File;
import com.sendsafely.Package;
import com.sendsafely.Recipient;
import com.sendsafely.dto.Confirmation;
import com.sendsafely.dto.request.UpdatePackageLifeRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.dto.response.ConfirmationResponse;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.PackageInformationResponse;
import com.sendsafely.dto.response.RecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UpdatePackageLifeException;
import com.sendsafely.upload.UploadManager;

public class UpdatePackageLifeHandler extends BaseHandler 
{	
	
	private UpdatePackageLifeRequest request = new UpdatePackageLifeRequest();
	
	public UpdatePackageLifeHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public void makeRequest(String packageId, int life) throws UpdatePackageLifeException {
		request.setPackageId(packageId);
		request.setPackageLife(life);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new UpdatePackageLifeException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws UpdatePackageLifeException
	{
		try {
			return send(request, new PackageInformationResponse());
		} catch (SendFailedException e) {
			throw new UpdatePackageLifeException(e);
		} catch (IOException e) {
			throw new UpdatePackageLifeException(e);
		}
	}
	
	protected Package convert(PackageInformationResponse obj)
	{
		Package info = new Package();
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
