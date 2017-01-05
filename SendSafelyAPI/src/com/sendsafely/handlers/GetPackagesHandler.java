package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sendsafely.dto.Confirmation;
import com.sendsafely.dto.request.GetPackagesRequest;
import com.sendsafely.dto.response.ConfirmationResponse;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.GetPackagesResponse;
import com.sendsafely.dto.response.PackageInformationResponse;
import com.sendsafely.dto.response.PackageListResponse;
import com.sendsafely.dto.response.RecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.GetPackagesException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.File;
import com.sendsafely.Package;
import com.sendsafely.PackageReference;
import com.sendsafely.Recipient;

public class GetPackagesHandler extends BaseHandler 
{	
	
	private GetPackagesRequest request;
	
	public GetPackagesHandler(UploadManager uploadManager, GetPackagesRequest request) {
		super(uploadManager);
		
		this.request = request;
	}

	public List<PackageReference> makeRequest() throws GetPackagesException {
		GetPackagesResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return convert(response.getPackages());
		}
		else
		{
			throw new GetPackagesException(response.getMessage());
		}
	}
	
	protected List<PackageReference> convert(List<PackageListResponse> rawPackages)
	{
		List<PackageReference> packages = new ArrayList<PackageReference>(rawPackages.size());
		for(PackageListResponse resp : rawPackages) {
			packages.add(convert(resp));
		}
		return packages;
	}
	
	protected PackageReference convert(PackageListResponse obj) {
		PackageReference info = new PackageReference();
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
	
	protected List<String> convertRecipients(List<String> responses) 
	{
		List<String> retval = new ArrayList<String>(responses.size());
		for (String resp : responses)
		{
			retval.add(resp);
		}
		
		return retval;
	}
	
	protected List<File> convertFiles(List<FileResponse> responses) 
	{
		if(responses == null) {
			return new ArrayList<File>();
		}
		
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
		f.setFileSize(resp.getFileSize());
		//f.setCreatedBy(resp.getCreatedByEmail());
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
	
	protected GetPackagesResponse send() throws GetPackagesException
	{
		try {
			return send(request, new GetPackagesResponse());
		} catch (SendFailedException e) {
			throw new GetPackagesException(e);
		} catch (IOException e) {
			throw new GetPackagesException(e);
		}
	}
}
