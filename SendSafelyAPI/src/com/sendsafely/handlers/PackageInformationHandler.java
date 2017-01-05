package com.sendsafely.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sendsafely.File;
import com.sendsafely.Package;
import com.sendsafely.Recipient;
import com.sendsafely.dto.Confirmation;
import com.sendsafely.dto.request.PackageInformationRequest;
import com.sendsafely.dto.response.ConfirmationResponse;
import com.sendsafely.dto.response.FileResponse;
import com.sendsafely.dto.response.PackageInformationResponse;
import com.sendsafely.dto.response.RecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class PackageInformationHandler extends BaseHandler 
{	
	
	private PackageInformationRequest request;
	
	public PackageInformationHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new PackageInformationRequest(uploadManager.getJsonManager());
	}

	public Package makeRequest(String packageId) throws PackageInformationFailedException {
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
	
	public Package makeRequestFromLink(String link) throws PackageInformationFailedException {
		URL url = createUrl(link);
		return makeRequestFromLink(url);
	}
	
	public Package makeRequestFromLink(URL link) throws PackageInformationFailedException {
		// Parse the link
		String packageCode = getPackageCode(link);
		String keyCode = getKeyCode(link);
		
		Package pkg = makeRequest(packageCode);
		pkg.setKeyCode(keyCode);
		return pkg;
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
		//info.setTopics(obj.getTopics());
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
		f.setFileSize(resp.getFileSize());
		f.setParts(resp.getParts());
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
			if(cr.getFile() != null) {
				c.setFile(createFile(cr.getFile()));
			}
			retval.add(c);
		}
		
		return retval;
	}
	
	private String getPackageCode(URL url) throws PackageInformationFailedException
	{
		String query = url.getQuery();
		
		// We only expect one parameter in the URL, the package code..
		String[] parameters = query.split("&");
		
		String packageCode = null; 
		for(String param : parameters) {
			// Parse out the key and the value.
			String[] parts = param.split("=");
			if(parts.length < 2) {
				throw new PackageInformationFailedException("Package Code Parameter not found");
			}
			
			String key = parts[0];
			String value = parts[1];
			
			if(parts.length > 2) {
				for(int i = 2; i<parts.length; i++) {
					value += parts[i];
				}
			}
			
			if(key.equals("packageCode")) {
				packageCode = value;
			}
		}
		
		if(packageCode == null) {
			throw new PackageInformationFailedException("Package Code Parameter not found");
		}
		
		return packageCode;
	}
	
	private String getKeyCode(URL url) throws PackageInformationFailedException
	{
		String query = url.toString();
		
		// We only expect one parameter in the URL, the package code..
		String[] parameters = query.split("#");
		
		if(parameters.length != 2) {
			throw new PackageInformationFailedException("Keycode could not be found");
		}
		
		String hash = parameters[1];
		String keyCode = hash.substring(hash.indexOf('=')+1);
				
		return keyCode;
	}
	
	private URL createUrl(String link) throws PackageInformationFailedException {
		try {
			return new URL(link);
		} catch (MalformedURLException e) {
			throw new PackageInformationFailedException(e);
		}
	}
	
}
