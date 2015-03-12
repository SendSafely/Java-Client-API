package com.sendsafely;

import java.util.List;

import com.sendsafely.connection.ConnectionFactory;
import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.credentials.CredentialsFactory;
import com.sendsafely.credentials.CredentialsManager;
import com.sendsafely.dto.EnterpriseInfo;
import com.sendsafely.dto.File;
import com.sendsafely.dto.PackageInformation;
import com.sendsafely.dto.PackageURL;
import com.sendsafely.dto.Recipient;
import com.sendsafely.dto.UserInformation;
import com.sendsafely.dto.response.VerifyVersionResponse;
import com.sendsafely.enums.CountryCode;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.Version;
import com.sendsafely.exceptions.AddRecipientFailedException;
import com.sendsafely.exceptions.ApproverRequiredException;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.DeletePackageException;
import com.sendsafely.exceptions.EnterpriseInfoFailedException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.GetPackagesException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UpdatePackageLifeException;
import com.sendsafely.exceptions.UpdateRecipientFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.exceptions.UserInformationFailedException;
import com.sendsafely.handlers.AddFileHandler;
import com.sendsafely.handlers.AddMessageHandler;
import com.sendsafely.handlers.AddRecipientHandler;
import com.sendsafely.handlers.CreatePackageHandler;
import com.sendsafely.handlers.DeletePackageHandler;
import com.sendsafely.handlers.EnterpriseInfoHandler;
import com.sendsafely.handlers.FinalizePackageHandler;
import com.sendsafely.handlers.GetPackagesHandler;
import com.sendsafely.handlers.HandlerFactory;
import com.sendsafely.handlers.PackageInformationHandler;
import com.sendsafely.handlers.UpdatePackageLifeHandler;
import com.sendsafely.handlers.UpdateRecipientHandler;
import com.sendsafely.handlers.UserInformationHandler;
import com.sendsafely.handlers.VerifyCredentialsHandler;
import com.sendsafely.handlers.VerifyVersionHandler;
import com.sendsafely.upload.UploadFactory;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.SendSafelyConfig;

/**
 * @author Erik Larsson
 * @description The main SendSafely API. Use this API to create packages and append files and recipients.
 */
public class SendSafely {

	private UploadManager uploadManager;
	
	protected double version = 0.3;
	
	/**
	 * @description The constructor to create a new SendSafely object.
	 * @param host The host name to connect to. Should be either https://www.sendsafely.com or https://www.sendsafely.co.uk
	 * @param apiKey The API key for the user. A new API key can be generated on the profile page for a user.
	 * @param apiSecret The secret belonging to the API key.
	 */
	public SendSafely(String host, String apiKey, String apiSecret)
	{
		CredentialsManager credentialsManager = CredentialsFactory.getDefaultCredentials(apiKey, apiSecret);
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		
		uploadManager = UploadFactory.getManager(connection, credentialsManager);
	}
	
	public SendSafely(String host, CredentialsManager credentialsManager)
	{
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		
		this.uploadManager = UploadFactory.getManager(connection, credentialsManager);
	}
	
	/**
	 * @description The constructor to create a new SendSafely object.
	 * @param connectionManager a custom connection manager used make requests to the server
	 * @param apiKey The API key for the user. A new API key can be generated on the profile page for a user.
	 * @param apiSecret The secret belonging to the API key.
	 */
	public SendSafely(ConnectionManager connectionManager, String apiKey, String apiSecret)
	{
		CredentialsManager credentialsManager = CredentialsFactory.getDefaultCredentials(apiKey, apiSecret);
		this.uploadManager = UploadFactory.getManager(connectionManager, credentialsManager);
	}
	
	public SendSafely(String host, ConnectionManager connectionManager, CredentialsManager credentialsManager)
	{	
		this.uploadManager = UploadFactory.getManager(connectionManager, credentialsManager);
	}

	/**
	 * @description Verifies the current version of the SendSafely API against the server. Returns an enum describing if the API needs to be updated or not.
	 * @throws SendFailedException
	 */
	public Version verifyVersion() throws SendFailedException
	{
		VerifyVersionHandler handler = (VerifyVersionHandler)HandlerFactory.getInstance(uploadManager, Endpoint.VerifyVersion);
		handler.setVersion(version);
		return handler.makeRequest();
	}
	
	/**
	 * @description Verifies a user's API key and secret. This method is typically called when a new user uses the API for the first time. Returns the email belonging to the authenticated user.
	 * @throws InvalidCredentialsException
	 */
	public String verifyCredentials() throws InvalidCredentialsException
	{
		VerifyCredentialsHandler handler = (VerifyCredentialsHandler)HandlerFactory.getInstance(uploadManager, Endpoint.VerifyCredentials);
		return handler.verify();
	}
	
	/**
	 * @description Gets information about the current user.
	 * @return A {@link UserInformationResponse} object.
	 * @throws InvalidCredentialsException
	 */
	public UserInformation getUserInformation() throws UserInformationFailedException
	{
		UserInformationHandler handler = (UserInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.USER_INFORMATION);
		return handler.makeRequest();
	}
	
	/**
	 * @description Retrieves information about the organization the user belongs to. 
	 * @return A {@link EnterpriseInfo} object.
	 * @throws EnterpriseInfoFailedException
	 */
	public EnterpriseInfo getEnterpriseInfo() throws EnterpriseInfoFailedException
	{
		EnterpriseInfoHandler handler = (EnterpriseInfoHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ENTERPRISE_INFO);
		return handler.makeRequest();
	}
	
	/**
	 * @description Creates a new package that can be used to attach files to. A new package must be created before files or recipients can be added. For further information about the package flow, see http://sendsafely.github.io/overview.htm
	 * @return A {@link PackageInformation} object containing information about the package.
	 * @throws CreatePackageFailedException
	 * @throws LimitExceededException
	 */
	public PackageInformation createPackage() throws CreatePackageFailedException, LimitExceededException
	{
		CreatePackageHandler handler = (CreatePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_PACKAGE);
		return handler.makeRequest();
	}
	
	/**
	 * @description Adds a recipient to a given package.
	 * @param The unique packageId that you are adding the recipient to
	 * @param The recipient email to be added
	 * @return A {@link Recipient} object containing information about the recipient.
	 * @throws LimitExceededException
	 * @throws AddRecipientFailedException
	 */
	public Recipient addRecipient(String packageId, String email) throws LimitExceededException, AddRecipientFailedException
	{
		AddRecipientHandler handler = (AddRecipientHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_RECIPIENT);
		return handler.makeRequest(packageId, email);
	}
	
	/**
	 * @description Adds a phone number to a given recipient.
	 * @param The unique packageId that you are updating
	 * @param The recipientId to be updated
	 * @param The phone number to associate with the recipient
	 * @param The country code that belongs to the phone number
	 * @throws UpdateRecipientFailedException
	 */
	public void addRecipientPhonenumber(String packageId, String recipientId, String phonenumber, CountryCode countryCode) throws UpdateRecipientFailedException
	{
		UpdateRecipientHandler handler = (UpdateRecipientHandler)HandlerFactory.getInstance(uploadManager, Endpoint.UPDATE_RECIPIENT);
		handler.makeRequest(packageId, recipientId, phonenumber, countryCode);
	}
	
	/**
	 * @description Deletes a package given a package ID.
	 * @param The unique packageId that you want to delete.
	 * @throws DeletePackageException
	 */
	public void deletePackage(String packageId) throws DeletePackageException
	{
		DeletePackageHandler handler = (DeletePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.DELETE_PACKAGE);
		handler.makeRequest(packageId);
	}
	
	/**
	 * @description Get all active packages
	 * @return List<String> a list of all active package IDs.
	 * @throws GetPackagesException
	 */
	public List<String> getActivePackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ACTIVE_PACKAGES);
		return handler.makeRequest();
	}
	
	/**
	 * @description Get all archived packages
	 * @return List<String> a list of all archived package IDs.
	 * @throws GetPackagesException
	 */
	public List<String> getArchivedPackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ARCHIVED_PACKAGES);
		return handler.makeRequest();
	}
	
	/**
	 * @description Fetch the latest package meta data from the server and return it
	 * @param packageId The packageId to fetch information for
	 * @return {@link PackageInformation}
	 * @throws PackageInformationFailedException
	 */
	public PackageInformation getPackageInformation(String packageId) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequest(packageId);
	}
	
	/**
	 * @description Update the package life. Setting the life to 0 means it will not expire
	 * @param packageId The packageId to fetch information for
	 * @param life The new package life
	 * @return {@link PackageInformation}
	 * @throws PackageInformationFailedException
	 */
	public void updatePackageLife(String packageId, int life) throws UpdatePackageLifeException
	{
		UpdatePackageLifeHandler handler = (UpdatePackageLifeHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_LIFE);
		handler.makeRequest(packageId, life);
	}
	
	/**
	 * @description Encrypt and upload a new file. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The packageId to attach the file to. 
	 * @param keyCode The keycode belonging to the package. 
	 * @param file The given file to encrypt and upload. This can not be a folder.
	 * @return {@link File} a file object with metadata for the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFile(String packageId, String keyCode, java.io.File file) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, keyCode, file);
	}
	
	/**
	 * @description Encrypt and upload a new file. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The packageId to attach the file to. 
	 * @param keyCode The keycode belonging to the package. 
	 * @param file The given file to encrypt and upload. This can not be a folder.
	 * @param progress A progress callback object which can be used to report back progress on how the upload is progressing.
	 * @return {@link File} a file object with metadata for the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFile(String packageId, String keyCode, java.io.File file, ProgressInterface progress) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, keyCode, file, progress);
	}
	
	/**
	 * @description Encrypt and upload a message. The message will be encrypted before being uploaded to the server.
	 * @param packageId The packageId to attach the message to. 
	 * @param keyCode The keycode belonging to the package. 
	 * @param message The message to encrypt and upload
	 * @return void
	 * @throws MessageException 
	 */
	public void encryptAndUploadMessage(String packageId, String keyCode, String message) throws MessageException
	{
		AddMessageHandler handler = (AddMessageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_MESSAGE);
		handler.makeRequest(packageId, keyCode, message);
	}
	
	/**
	 * @description Finalizes the package so it can be delivered to the recipients
	 * @param packageId The packageId which is to be finalized. 
	 * @param keycode The keycode belonging to the package.
	 * @return A link to access the package. This link can be sent to the recipients.
	 * @throws LimitExceededException
	 * @throws FinalizePackageFailedException
	 * @throws ApproverRequiredException 
	 */
	public PackageURL finalizePackage(String packageId, String keycode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException
	{
		FinalizePackageHandler handler = (FinalizePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FINALIZE_PACKAGE);
		return handler.makeRequest(packageId, keycode);
	}
	
}
