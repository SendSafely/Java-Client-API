package com.sendsafely.handlers;

import java.util.HashMap;
import java.util.Map;

import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.request.CreateFileIdRequest;
import com.sendsafely.dto.request.CreatePackageRequest;
import com.sendsafely.dto.request.EnterpriseInfoRequest;
import com.sendsafely.dto.request.FinalizePackageRequest;
import com.sendsafely.dto.request.GetPackagesRequest;
import com.sendsafely.dto.request.PackageInformationRequest;
import com.sendsafely.dto.request.UpdatePackageLifeRequest;
import com.sendsafely.dto.request.UpdateRecipientRequest;
import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.dto.request.UserInformationRequest;
import com.sendsafely.dto.request.VerifyCredentialsRequest;
import com.sendsafely.dto.request.VerifyVersionRequest;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.SendSafelyConfig;

public class HandlerFactory 
{	
	Map<Endpoint, BaseHandler> endpoints = new HashMap<Endpoint, BaseHandler>();
	
	protected HandlerFactory(UploadManager uploadManager)
	{
		initialize(uploadManager);
	}
	
	private void initialize(UploadManager uploadManager)
	{
		GetPackagesRequest activePackageRequest = new GetPackagesRequest(HTTPMethod.GET, "/package/");
		GetPackagesRequest archivedPackageRequest = new GetPackagesRequest(HTTPMethod.GET, "/package/archived/");
		
		endpoints.put(Endpoint.VerifyVersion, new VerifyVersionHandler(uploadManager));
		endpoints.put(Endpoint.VerifyCredentials, new VerifyCredentialsHandler(uploadManager));
		endpoints.put(Endpoint.USER_INFORMATION, new UserInformationHandler(uploadManager));
		endpoints.put(Endpoint.ENTERPRISE_INFO, new EnterpriseInfoHandler(uploadManager));
		endpoints.put(Endpoint.CREATE_PACKAGE, new CreatePackageHandler(uploadManager));
		endpoints.put(Endpoint.ADD_RECIPIENT, new AddRecipientHandler(uploadManager));
		endpoints.put(Endpoint.UPDATE_RECIPIENT, new UpdateRecipientHandler(uploadManager));
		endpoints.put(Endpoint.DELETE_PACKAGE, new DeletePackageHandler(uploadManager));
		endpoints.put(Endpoint.PACKAGE_INFORMATION, new PackageInformationHandler(uploadManager));
		endpoints.put(Endpoint.PACKAGE_LIFE, new UpdatePackageLifeHandler(uploadManager));
		endpoints.put(Endpoint.ACTIVE_PACKAGES, new GetPackagesHandler(uploadManager, activePackageRequest));
		endpoints.put(Endpoint.ARCHIVED_PACKAGES, new GetPackagesHandler(uploadManager, archivedPackageRequest));
		endpoints.put(Endpoint.FINALIZE_PACKAGE, new FinalizePackageHandler(uploadManager));
		endpoints.put(Endpoint.ADD_FILE, new AddFileHandler(uploadManager));
		endpoints.put(Endpoint.UPLOAD_FILE, new UploadFileHandler(uploadManager));
		endpoints.put(Endpoint.ADD_MESSAGE, new AddMessageHandler(uploadManager));
	}
	
	public static BaseHandler getInstance(UploadManager uploadManager, Endpoint p)
	{
		HandlerFactory factory = new HandlerFactory(uploadManager);
		
		return factory.endpoints.get(p);
	}
	
}
