package com.sendsafely.handlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sendsafely.File;
import com.sendsafely.PackageReference;
import com.sendsafely.PackageSearchResults;
import com.sendsafely.dto.request.GetOrganizationPackagesRequest;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.GetOrganizationPackagesResponse;
import com.sendsafely.dto.response.PackageListResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.PackageStatus;
import com.sendsafely.exceptions.GetPackagesException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetOrganizationPackagesHandler extends BaseHandler {

	private GetOrganizationPackagesRequest request;
	
	public GetOrganizationPackagesHandler(UploadManager uploadManager) {
		super(uploadManager);
		
		this.request = new GetOrganizationPackagesRequest(uploadManager.getJsonManager());;
	}

	public PackageSearchResults makeRequest(Date fromDate, Date toDate, String sender, PackageStatus status,
			String recipient, String fileName) throws GetPackagesException {
		//Convert Date to String to pass to server. 
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		String fromDateStr = fromDate==null?"":outputDateFormat.format(fromDate);
		String toDateStr = toDate==null?"":outputDateFormat.format(toDate); 
		request.setFromDate(fromDateStr);
		request.setToDate(toDateStr);
		request.setSender(sender);
		request.setRecipient(recipient);
		request.setFileName(fileName);
		
		if (status != null)
		{
			request.setStatus(status.toString());
		}
		
		GetOrganizationPackagesResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new GetPackagesException(response.getMessage());
		}
		return convert(response);
	}
	
	private PackageSearchResults convert(GetOrganizationPackagesResponse response) {
		List<PackageReference> packages = new ArrayList<PackageReference>(response.getPackages().size());
		for(PackageListResponse resp : response.getPackages()) {
			packages.add(convert(resp));
		}
		PackageSearchResults organizationPackages = new PackageSearchResults();
		organizationPackages.setPackages(packages);
		organizationPackages.setCapped(response.isCapped());
		return organizationPackages;
	}
	protected PackageReference convert(PackageListResponse obj) {
		PackageReference info = new PackageReference();
		info.setApproverList(obj.getApproverList());
		info.setLife(obj.getLife());
		info.setFiles(convertFiles(obj.getFiles()));
		info.setNeedsApproval(obj.getNeedsApproval());
		info.setPackageCode(obj.getPackageCode());
		info.setPackageId(obj.getPackageId());
		info.setRecipients(obj.getRecipients());
		info.setContactGroupNames(obj.getContactGroups());
		info.setServerSecret(obj.getServerSecret());
		info.setStatus(convert(obj.getPackageState()));
		info.setPackageOwner(obj.getPackageUserName());
		info.setPackageTimestamp(obj.getPackageUpdateTimestamp());
		return info;
	}

	private PackageStatus convert(int packageState) {
		
		if (packageState < 0)
		{
			return PackageStatus.ARCHIVED;
		}
		else if (packageState == 1 || packageState == 2 || packageState == 6)
		{
			return PackageStatus.EXPIRED;
		}
		else
		{
			return PackageStatus.ACTIVE;
		}
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
	protected GetOrganizationPackagesResponse send() throws GetPackagesException 
	{
		try {
			return send(request, new GetOrganizationPackagesResponse());
		} catch (IOException | SendFailedException e) {
			throw new GetPackagesException(e);
		}
	}
}
