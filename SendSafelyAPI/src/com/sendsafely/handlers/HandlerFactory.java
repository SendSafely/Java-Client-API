package com.sendsafely.handlers;

import java.util.HashMap;
import java.util.Map;

import com.sendsafely.dto.request.GetPackagesRequest;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.upload.UploadManager;

public class HandlerFactory 
{	
	Map<Endpoint, BaseHandler> endpoints = new HashMap<Endpoint, BaseHandler>();
	
	protected HandlerFactory(UploadManager uploadManager)
	{
		initialize(uploadManager);
	}
	
	private void initialize(UploadManager uploadManager)
	{
		GetPackagesRequest activePackageRequest = new GetPackagesRequest(uploadManager.getJsonManager(), HTTPMethod.GET, "/package/");
		GetPackagesRequest archivedPackageRequest = new GetPackagesRequest(uploadManager.getJsonManager(), HTTPMethod.GET, "/package/archived/");
		
		endpoints.put(Endpoint.VerifyVersion, new VerifyVersionHandler(uploadManager));
		endpoints.put(Endpoint.VerifyCredentials, new VerifyCredentialsHandler(uploadManager));
		endpoints.put(Endpoint.USER_INFORMATION, new UserInformationHandler(uploadManager));
		endpoints.put(Endpoint.ENTERPRISE_INFO, new EnterpriseInfoHandler(uploadManager));
		endpoints.put(Endpoint.CREATE_PACKAGE, new CreatePackageHandler(uploadManager));
		endpoints.put(Endpoint.UPDATE_RECIPIENT, new UpdateRecipientHandler(uploadManager));
		endpoints.put(Endpoint.DELETE_PACKAGE, new DeletePackageHandler(uploadManager));
		endpoints.put(Endpoint.PACKAGE_INFORMATION, new PackageInformationHandler(uploadManager));
		endpoints.put(Endpoint.PACKAGE_LIFE, new UpdatePackageLifeHandler(uploadManager));
		endpoints.put(Endpoint.ACTIVE_PACKAGES, new GetPackagesHandler(uploadManager, activePackageRequest));
		endpoints.put(Endpoint.ARCHIVED_PACKAGES, new GetPackagesHandler(uploadManager, archivedPackageRequest));
		endpoints.put(Endpoint.FINALIZE_PACKAGE, new FinalizePackageHandler(uploadManager));
		endpoints.put(Endpoint.ADD_FILE, new AddFileHandler(uploadManager));
		endpoints.put(Endpoint.UPLOAD_FILE, new UploadFileHandler(uploadManager));
		endpoints.put(Endpoint.ORGANIZATION_PACKAGES, new GetOrganizationPackagesHandler(uploadManager));
		endpoints.put(Endpoint.CREATE_DIRECTORY, new CreateDirectoryHandler(uploadManager));
		endpoints.put(Endpoint.GET_DIRECTORY, new GetDirectoryHandler(uploadManager));
		endpoints.put(Endpoint.PACKAGE_NAME, new UpdatePackageDescriptorHandler(uploadManager));
		endpoints.put(Endpoint.MOVE_DIRECTORY, new MoveDirectoryHandler(uploadManager));
		endpoints.put(Endpoint.MOVE_FILE, new MoveFileHandler(uploadManager));
		endpoints.put(Endpoint.FILE_INFORMATION, new FileInformationHandler(uploadManager));
		endpoints.put(Endpoint.DELETE_FILE, new DeleteFileHandler(uploadManager));
		endpoints.put(Endpoint.DIRECTORY_NAME, new UpdateDirectoryNameHandler(uploadManager));
		endpoints.put(Endpoint.DELETE_DIRECTORY, new DeleteDirectoryHandler(uploadManager));
		endpoints.put(Endpoint.GET_ACTIVITY_LOG, new GetActivityLogHandler(uploadManager));
	}
	
	public static BaseHandler getInstance(UploadManager uploadManager, Endpoint p)
	{
		HandlerFactory factory = new HandlerFactory(uploadManager);
		
		return factory.endpoints.get(p);
	}
	
}
