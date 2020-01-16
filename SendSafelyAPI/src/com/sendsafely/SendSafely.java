package com.sendsafely;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;

import com.google.gson.Gson;
import com.sendsafely.connection.ConnectionFactory;
import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.credentials.CredentialsFactory;
import com.sendsafely.credentials.CredentialsManager;
import com.sendsafely.credentials.DefaultCredentials;
import com.sendsafely.dto.ActivityLogEntry;
import com.sendsafely.dto.EnterpriseInfo;
import com.sendsafely.dto.FileInfo;
import com.sendsafely.dto.PackageURL;
import com.sendsafely.dto.RecipientHistory;
import com.sendsafely.dto.UserInformation;
import com.sendsafely.dto.request.AddContactGroupAsRecipientRequest;
import com.sendsafely.dto.request.AddDropzoneRecipientRequest;
import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.request.AddRecipientsRequest;
import com.sendsafely.dto.request.AddUserToContactGroupRequest;
import com.sendsafely.dto.request.GetContactGroupsRequest;
import com.sendsafely.dto.request.GetDropzoneRecipientRequest;
import com.sendsafely.dto.request.GetMessageRequest;
import com.sendsafely.dto.request.GetOrganizationContactGroupsRequest;
import com.sendsafely.dto.request.RemoveContactGroupAsRecipientRequest;
import com.sendsafely.dto.request.RemoveContactGroupRequest;
import com.sendsafely.dto.request.RemoveUserFromContactGroupRequest;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.CountryCode;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.PackageState;
import com.sendsafely.enums.PackageStatus;
import com.sendsafely.enums.Version;
import com.sendsafely.exceptions.AddEmailContactGroupFailedException;
import com.sendsafely.exceptions.ApproverRequiredException;
import com.sendsafely.exceptions.ContactGroupException;
import com.sendsafely.exceptions.CreateContactGroupFailedException;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.DeletePackageException;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.DropzoneRecipientFailedException;
import com.sendsafely.exceptions.EnterpriseInfoFailedException;
import com.sendsafely.exceptions.FileOperationFailedException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.GetActivityLogException;
import com.sendsafely.exceptions.GetContactGroupsFailedException;
import com.sendsafely.exceptions.GetKeycodeFailedException;
import com.sendsafely.exceptions.GetPackagesException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.exceptions.PublicKeysFailedException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.RemoveContactGroupAsRecipientFailedException;
import com.sendsafely.exceptions.RemoveContactGroupFailedException;
import com.sendsafely.exceptions.RemoveEmailContactGroupFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.TwoFactorAuthException;
import com.sendsafely.exceptions.UpdatePackageDescriptorFailedException;
import com.sendsafely.exceptions.UpdatePackageLifeException;
import com.sendsafely.exceptions.UpdateRecipientFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.exceptions.UserInformationFailedException;
import com.sendsafely.file.FileManager;
import com.sendsafely.handlers.AddContactGroupAsRecipientHandler;
import com.sendsafely.handlers.AddDropzoneRecipientHandler;
import com.sendsafely.handlers.AddFileHandler;
import com.sendsafely.handlers.AddMessageHandler;
import com.sendsafely.handlers.AddRecipientHandler;
import com.sendsafely.handlers.AddRecipientsHandler;
import com.sendsafely.handlers.AddUserContactGroupHandler;
import com.sendsafely.handlers.CreateContactGroupHandler;
import com.sendsafely.handlers.CreateDirectoryHandler;
import com.sendsafely.handlers.CreatePackageHandler;
import com.sendsafely.handlers.DeleteDirectoryHandler;
import com.sendsafely.handlers.DeleteFileHandler;
import com.sendsafely.handlers.DeletePackageHandler;
import com.sendsafely.handlers.RevokePublicKey;
import com.sendsafely.handlers.DeleteTempPackageHandler;
import com.sendsafely.handlers.DownloadAndDecryptFileHandler;
import com.sendsafely.handlers.EnterpriseInfoHandler;
import com.sendsafely.handlers.FileInformationHandler;
import com.sendsafely.handlers.FinalizePackageHandler;
import com.sendsafely.handlers.GetActivityLogHandler;
import com.sendsafely.handlers.GetContactGroupsHandler;
import com.sendsafely.handlers.GetDirectoryHandler;
import com.sendsafely.handlers.GetDropzoneRecipientHandler;
import com.sendsafely.handlers.GetKeycode;
import com.sendsafely.handlers.GetMessageHandler;
import com.sendsafely.handlers.GetOrganizationPackagesHandler;
import com.sendsafely.handlers.GetPackagesHandler;
import com.sendsafely.handlers.GetRecipientHandler;
import com.sendsafely.handlers.GetRecipientHistoryHandler;
import com.sendsafely.handlers.HandlerFactory;
import com.sendsafely.handlers.MoveDirectoryHandler;
import com.sendsafely.handlers.MoveFileHandler;
import com.sendsafely.handlers.PackageInformationHandler;
import com.sendsafely.handlers.ParseLinksHandler;
import com.sendsafely.handlers.RemoveContactGroupAsRecipientHandler;
import com.sendsafely.handlers.RemoveContactGroupHandler;
import com.sendsafely.handlers.RemoveDropzoneRecipientHandler;
import com.sendsafely.handlers.RemoveRecipientHandler;
import com.sendsafely.handlers.RemoveUserContactGroupHandler;
import com.sendsafely.handlers.UpdateDirectoryNameHandler;
import com.sendsafely.handlers.UpdatePackageDescriptorHandler;
import com.sendsafely.handlers.UpdatePackageLifeHandler;
import com.sendsafely.handlers.UpdateRecipientHandler;
import com.sendsafely.handlers.UploadPublicKey;
import com.sendsafely.handlers.UserInformationHandler;
import com.sendsafely.handlers.VerifyCredentialsHandler;
import com.sendsafely.handlers.VerifyVersionHandler;
import com.sendsafely.json.JsonManager;
import com.sendsafely.progress.DefaultProgress;
import com.sendsafely.upload.DefaultUploadManager;
import com.sendsafely.upload.UploadFactory;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;
import com.sendsafely.utils.Keypair;

/**
 * @description The main SendSafely API. Use this API to create packages and append files and recipients.
 */
public class SendSafely {
	
	private UploadManager uploadManager;
	
	private JsonManager jsonManager = null;
	
	protected double version = 0.3;
	
	private boolean ec2Proxy = false;
	
	/**
	 * @description Constructor to create a new SendSafely object.
	 * @param host The hostname you use to access SendSafely. Should be https://companyname.sendsafely.com or https://www.sendsafely.com
	 * @param apiKey The API key for the user. A new API key can be generated on the user's Edit Profile page of the SendSafely web portal or using the generateAPIKey API method. 
	 * @param apiSecret The secret belonging to the API key. 
	 */
	public SendSafely(String host, String apiKey, String apiSecret)
	{
		Security.addProvider(new BouncyCastleProvider());
		CredentialsManager credentialsManager = CredentialsFactory.getDefaultCredentials(apiKey, apiSecret);
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		
		uploadManager = UploadFactory.getManager(connection, credentialsManager);
	}
	
	/**
	 * @description Constructor to create a new SendSafely object.
	 * @param host The hostname you use to access SendSafely. Should be https://companyname.sendsafely.com or https://www.sendsafely.com
	 * @param credentialsManager Class implementing the CredentialsManager interface for managing the api key and api secret. 
	 */
	public SendSafely(String host, CredentialsManager credentialsManager)
	{
		Security.addProvider(new BouncyCastleProvider());
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		this.uploadManager = UploadFactory.getManager(connection, credentialsManager);
	}
	
	/**
	 * @description Constructor to create a new SendSafely object.
	 * @param connectionManager Class implementing the ConnectionManager interface for managing connections with the server.
	 * @param apiKey The API key for the user. A new API key can be generated on the user's Edit Profile page of the SendSafely web portal or using the generateAPIKey API method.  
	 * @param apiSecret The secret belonging to the API key.
	 */
	public SendSafely(ConnectionManager connectionManager, String apiKey, String apiSecret)
	{
		Security.addProvider(new BouncyCastleProvider());
		CredentialsManager credentialsManager = CredentialsFactory.getDefaultCredentials(apiKey, apiSecret);
		this.uploadManager = UploadFactory.getManager(connectionManager, credentialsManager);
	}
	
	/**
	 * @description Constructor to create a new SendSafely object.
	 * @param host The hostname you use to access SendSafely. Should be https://companyname.sendsafely.com or https://www.sendsafely.com
	 * @param connectionManager Class implementing the ConnectionManager interface for managing communication with the server.
	 * @param credentialsManager Class implementing the CredentialsManager interface for managing the api key and api secret.  
	 */
	public SendSafely(String host, ConnectionManager connectionManager, CredentialsManager credentialsManager)
	{	
		Security.addProvider(new BouncyCastleProvider());
		this.uploadManager = UploadFactory.getManager(connectionManager, credentialsManager);
	}
	
	/**
	 * @description Constructor to create a new SendSafely object.
	 * @param uploadManager Class implementing the UploadManager interface for handling file uploads to the server. The implementing class is also responsible for connection and credential management.
	 */
    public SendSafely(UploadManager uploadManager)
    {
        this.uploadManager = uploadManager;
    }
	
    /**
     * @description Constructor to create a new SendSafely object. This is intended to facilitate a username/password login through the Java API.
     * @param host The hostname you use to access SendSafely. Should be https://companyname.sendsafely.com or https://www.sendsafely.com
     */
	public SendSafely(String host) {
		this(host, (ConnectionManager)null, (CredentialsManager)null);
	}
	
	public void setEc2Proxy (boolean isProxy) {
		this.ec2Proxy = isProxy;
	}
	
	/**
	 * @description Add Contact Group as a recipient on a package.
	 * @param packageId The unique package id of the package for the add the Contact Group operation.
	 * @param groupId The unique id of the Contact Group to add to the package.
	 * @throws ContactGroupException
	 */
	public void addContactGroupToPackage(String packageId, String groupId) throws ContactGroupException{
		AddContactGroupAsRecipientHandler handler = new AddContactGroupAsRecipientHandler(uploadManager, new AddContactGroupAsRecipientRequest(uploadManager.getJsonManager(), packageId, groupId));
		handler.makeRequest();
	}
	
	/**
	 * @description Adds a recipient email address to the current user's Dropzone.
	 * @param email The recipient email address added to the Dropzone.
	 * @throws RecipientFailedException
	 */
	public void addDropzoneRecipient(String email) throws RecipientFailedException
	{
		AddDropzoneRecipientHandler handler = new AddDropzoneRecipientHandler(uploadManager, new AddDropzoneRecipientRequest(uploadManager.getJsonManager(), email));
		handler.addDropzoneRecipient(email);
	}
	
	/**
	 * @description Adds a recipient to a given package.
	 * @param packageId The unique package id of the package for the add recipient operation.
	 * @param email The recipient email to be added.
	 * @returnType Recipient
	 * @return A Recipient object containing information about the recipient.
	 * @throws LimitExceededException
	 * @throws RecipientFailedException
	 */
	public Recipient addRecipient(String packageId, String email) throws LimitExceededException, RecipientFailedException
	{
		AddRecipientHandler handler = new AddRecipientHandler(uploadManager, new AddRecipientRequest(uploadManager.getJsonManager(), email));
		return handler.addRecipient(packageId);
	}

	/**
	 * @description Adds a phone number to a given recipient.
	 * @param packageId The unique packageId that you are updating.
	 * @param recipientId The recipientId to be updated.
	 * @param phonenumber The phone number to associate with the recipient. Passing a phone number with a numeric country code prefix (i.e. +44), will effectively override the countryCode parameter.
	 * @param countryCode The country code that belongs to the phone number.
	 * @throws UpdateRecipientFailedException
	 */
	public void addRecipientPhonenumber(String packageId, String recipientId, String phonenumber, CountryCode countryCode) throws UpdateRecipientFailedException
	{
		UpdateRecipientHandler handler = (UpdateRecipientHandler)HandlerFactory.getInstance(uploadManager, Endpoint.UPDATE_RECIPIENT);
		handler.makeRequest(packageId, recipientId, phonenumber, countryCode);
	}
	
	/**
	 * @description Adds a list of recipients to a given package.
	 * @param packageId The unique package id of the package for the add recipient operation.
	 * @param emails The list of recipients to be added.
	 * @returnType List<Recipient>
	 * @return A list containing information about the recipients.
	 * @throws LimitExceededException
	 * @throws RecipientFailedException
	 */
	public List<Recipient> addRecipients(String packageId, List<String> emails) throws LimitExceededException, RecipientFailedException
	{
		AddRecipientsRequest request = new AddRecipientsRequest(uploadManager.getJsonManager(), emails);
		AddRecipientsHandler handler = new AddRecipientsHandler(uploadManager, request);
		return handler.makeRequest(packageId);
	}
	
	/**
	 * @description Add user email address to the specified Contact Group.  
	 * @param groupId The unique id of the Contact Group for the add user operation.
	 * @param userEmail The email address to add to the Contact Group.
	 * @returnType String
	 * @return The unique id of the user associated with the email address added to the Contact Group.
	 * @throws AddEmailContactGroupFailedException
	 */
	public String addUserToContactGroup(String groupId, String userEmail) throws AddEmailContactGroupFailedException{
		AddUserContactGroupHandler handler = new AddUserContactGroupHandler(uploadManager, new AddUserToContactGroupRequest(uploadManager.getJsonManager(), groupId, userEmail));
		return handler.makeRequest();
	}
	
	/**
	 * @description Create a new Contact Group with the passed in group name. A Contact Group allows a user to define and manage a group of recipients at the group-level, rather than individually on each package. For more information about Contact Groups, refer to http://sendsafely.github.io/overview.htm 	 
	 * @param groupName The name of the new Contact Group.  
	 * @returnType String
	 * @return The unique group id of the created Contact Group. This value is required for subsequent Contact Group management operations.  
	 * @throws CreateContactGroupFailedException
	 */
	public String createContactGroup(String groupName) throws CreateContactGroupFailedException{
		CreateContactGroupHandler handler = new CreateContactGroupHandler(uploadManager);
		return handler.makeRequest(groupName);
	}
	
	/**
	 * @description Create a new Enterprise Contact Group with the passed in group name. The method caller must be a SendSafely Enterprise Administrator, and the Contact Group it creates is available to all users in an organization. For more information on Contact Groups, refer to http://sendsafely.github.io/overview.htm	 
	 * @param groupName The name of the new Contact Group.  
	 * @param isEnterpriseGroup A boolean flag for determining whether a Contact Group is an Enterprise Contact Group. If set to true, subsequent management operations of the Contact Group will require SendSafely Enterprise Administrator privileges, however the Contact Group can be added as a recipient to any package by any user in the organization.   
	 * @returnType String
	 * @return The unique group id of the created Contact Group. This value is required for subsequent Contact Group management operations.
	 * @throws CreateContactGroupFailedException
	 */
	public String createContactGroup(String groupName, boolean isEnterpriseGroup) throws CreateContactGroupFailedException{
		CreateContactGroupHandler handler = new CreateContactGroupHandler(uploadManager);
		return handler.makeRequest(groupName, isEnterpriseGroup);
	}
		
	/**
	 * @description Creates a new directory in a Workspace. Only Workspace packages support directories.
	 * @param packageId The unique package id of the package for the create directory operation.
	 * @param parentDirectoryId The unique id of the created directory's parent directory. If creating a directory in the root directory of a Workspace, this will be the packageDirectoryId property of the Workspace Package object. Otherwise, this will be the directoryId property of the parent Directory object. 
	 * @param directoryName The name of the new directory to be created.
	 * @returnType Directory
	 * @return A Directory object containing information about the created directory.
	 * @throws DirectoryOperationFailedException
	 */
	public Directory createDirectory(String packageId, String parentDirectoryId, String directoryName) throws DirectoryOperationFailedException{
		CreateDirectoryHandler handler = (CreateDirectoryHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_DIRECTORY);
		String directoryId = handler.makeRequest(packageId, parentDirectoryId, directoryName);
		Directory dir = this.getDirectory(packageId, directoryId);
		return dir;
	}
	
	/**
	 * @description Creates a new package for the purpose of sending files. A new package must be created before files or recipients can be added. For further information about the package flow, see http://sendsafely.github.io/overview.htm
	 * @returnType Package
	 * @return A Package object containing information about the package.
	 * @throws CreatePackageFailedException
	 * @throws LimitExceededException
	 */
	public Package createPackage() throws CreatePackageFailedException, LimitExceededException
	{
		CreatePackageHandler handler = (CreatePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_PACKAGE);
		return handler.makeRequest();
	}
	
	/**
	 * @description Creates a new package that represents a secure Workspace. A Workspace is a type of package that supports file collaboration features, such as directories and subdirectories, file versioning, role-based access control, and activity logging. A Workspace must be created before files, directories, or collaborators can be added. For further information about the package flow and Workspaces, refer to http://sendsafely.github.io/overview.htm
	 * @param isWorkspace Flag declaring the package is a Workspace.
	 * @returnType Package
	 * @return A Package object containing information about the package.
	 * @throws CreatePackageFailedException
	 * @throws LimitExceededException
	 */
	public Package createPackage(Boolean isWorkspace) throws CreatePackageFailedException, LimitExceededException
	{
		CreatePackageHandler handler = (CreatePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_PACKAGE);
		handler.setVDR(isWorkspace);
		return handler.makeRequest();
	}
	
	/**
	 * @description Creates a new package and assigns package owner to the user whose email address is passed as the method argument. The method caller must be a SendSafely Enterprise Administrator and in the same organization as the assigned package owner.   
	 * @param email The email address of the package owner.
	 * @returnType Package
	 * @return A Package object containing information about the package.
	 * @throws CreatePackageFailedException
	 * @throws LimitExceededException
	 */
	public Package createPackageForUser(String email) throws CreatePackageFailedException, LimitExceededException
	{
		CreatePackageHandler handler = (CreatePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_PACKAGE);
		return handler.makeRequest(email);
	}
	
	/**
	 * @description Delete the Contact Group associated with the passed in group id. 
	 * @param groupId The unique id of the Contact Group to delete.
	 * @throws RemoveContactGroupFailedException
	 */
	public void deleteContactGroup(String groupId) throws RemoveContactGroupFailedException{
		RemoveContactGroupHandler handler = new RemoveContactGroupHandler(uploadManager, new RemoveContactGroupRequest(uploadManager.getJsonManager(), groupId));
		handler.makeRequest();
	}
	
	/**
	 * @description Deletes a directory from a Workspace package.
	 * @param packageId The unique package id of the package for the delete directory operation.
	 * @param directoryId The unique directory id of the directory to delete.
	 * @throws DirectoryOperationFailedException
	 */
	public void deleteDirectory(String packageId, String directoryId) throws DirectoryOperationFailedException{
		DeleteDirectoryHandler handler = (DeleteDirectoryHandler)HandlerFactory.getInstance(uploadManager, Endpoint.DELETE_DIRECTORY);
		handler.makeRequest(packageId, directoryId);
	}
	
	/**
	 * @description Deletes a file from a Workspace package.
	 * @param packageId The unique package id of the package for the delete file operation.
	 * @param directoryId The unique directory id of the directory containing the file to delete.
	 * @param fileId The unique file id of the file to delete.
	 * @throws FileOperationFailedException
	 */
	public void deleteFile(String packageId, String directoryId, String fileId) throws FileOperationFailedException {
		DeleteFileHandler handler = (DeleteFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.DELETE_FILE);
		handler.makeRequest(packageId, directoryId, fileId);
	}
	
	/**
	 * @description Deletes a package with the given package id.
	 * @param packageId The unique package id of the package to be deleted.
	 * @throws DeletePackageException
	 */
	public void deletePackage(String packageId) throws DeletePackageException
	{
		DeletePackageHandler handler = (DeletePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.DELETE_PACKAGE);
		handler.makeRequest(packageId);
	}
	
	/**
	 * @description Downloads a file from the server and decrypts it.
	 * @param packageId The unique package id of the package for the file download operation.
	 * @param fileId The unique file id of the file to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @returnType java.io.File
	 * @return A java.io.File object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException 
	 * @throws PasswordRequiredException
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode) throws DownloadFileException, PasswordRequiredException
	{
		return downloadFile(packageId, fileId, keyCode, new DefaultProgress());
	}
	
	/**
	 * @description Downloads a file from the server and decrypts it.
	 * @param packageId The unique package id of the package for the file download operation.
	 * @param fileId The unique file id of the file to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @param progress A progress callback object which can be used to report back progress on how the download is progressing.
	 * @returnType java.io.File
	 * @return A java.io.File object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException 
	 * @throws PasswordRequiredException
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode, ProgressInterface progress) throws DownloadFileException, PasswordRequiredException
	{
		DownloadAndDecryptFileHandler handler = new DownloadAndDecryptFileHandler(uploadManager);
		return handler.makeRequestS3(packageId, null, fileId, keyCode, progress, null, ec2Proxy);
	}
	
	/**
	 * @description Retrieves a list of packages where the passed in email address is a package recipient.
	 * @param recipientEmail The email address of the recipient.
	 * @returnType List<RecipientHistory>
	 * @return A List<RecipientHistory> containing information about each package retrieved, including confirmed downloads for the recipient.
	 * @throws RecipientFailedException
	 */
	public List<RecipientHistory> getRecipientHistory(String recipientEmail) throws RecipientFailedException{ 
		GetRecipientHistoryHandler handler = ((GetRecipientHistoryHandler)HandlerFactory.getInstance(uploadManager, Endpoint.RECIPIENT_HISTORY));
		return handler.makeRequest(recipientEmail);
	}
	
	/**
	 * @description Downloads a file from the server and decrypts it.
	 * @param packageId The unique package id of the package for the file download operation.
	 * @param fileId The unique file id of the file to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @param progress A progress callback object which can be used to report back progress on how the download is progressing.
	 * @param password The password required to download a file from a password protected undisclosed package (i.e. a package without any recipients assigned).
	 * @returnType java.io.File
	 * @return A java.io.File object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException 
	 * @throws PasswordRequiredException
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode, ProgressInterface progress, String password) throws DownloadFileException, PasswordRequiredException
	{
		DownloadAndDecryptFileHandler handler = new DownloadAndDecryptFileHandler(uploadManager);
		return handler.makeRequestS3(packageId, null, fileId, keyCode, progress, password, ec2Proxy);
	}
	
	/**
	 * @description Downloads a file from the server and decrypts it.
	 * @param packageId The unique package id of the package for the file download operation.
	 * @param fileId The unique file id of the file to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @param password The password required to download a file from a password protected undisclosed package (i.e. a package without any recipients assigned).
	 * @returnType java.io.File
	 * @return A java.io.File object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException 
	 * @throws PasswordRequiredException
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode, String password) throws DownloadFileException, PasswordRequiredException
	{
		DownloadAndDecryptFileHandler handler = new DownloadAndDecryptFileHandler(uploadManager);
		return handler.makeRequestS3(packageId, null, fileId, keyCode, new DefaultProgress(), password, ec2Proxy);
	}
	
	/**
	 * @description Downloads a file located in a directory of a Workspace package from the server and decrypts it.
	 * @param packageId The unique package id of the Workspace package for the file download operation.
	 * @param directoryId The unique directory id of the directory for the file download operation.  
	 * @param fileId The unique file id of the file to download.
	 * @param keyCode The keycode belonging to the package.
	 * @returnType java.io.File
	 * @return A java.io.File object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException
	 * @throws PasswordRequiredException
	 */
	public java.io.File downloadFileFromDirectory(String packageId, String directoryId, String fileId, String keyCode) throws DownloadFileException, PasswordRequiredException {
		return downloadFileFromDirectory(packageId, directoryId, fileId, keyCode, new DefaultProgress());
	}
	
	/**
	 * @description Downloads a file located in a directory of a Workspace package from the server and decrypts it.
	 * @param packageId The unique package id of the Workspace package for the file download operation.
	 * @param directoryId The unique directory id of the directory for the file download operation.  
	 * @param fileId The unique file id of the file to download.
	 * @param keyCode The keycode belonging to the package.
	 * @param progress A progress callback object which can be used to report back progress on how the upload is progressing.
	 * @returnType java.io.File
	 * @return A java.io.File object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException
	 * @throws PasswordRequiredException
	 */
	public java.io.File downloadFileFromDirectory(String packageId, String directoryId, String fileId, String keyCode, ProgressInterface progress) throws DownloadFileException, PasswordRequiredException {
		DownloadAndDecryptFileHandler handler = new DownloadAndDecryptFileHandler(uploadManager);
		return handler.makeRequestS3(packageId, directoryId, fileId, keyCode, progress, (String)null, ec2Proxy);
	}
	
	/**
	 * @description Encrypt and upload a new file. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The unique package id of the package for the file upload operation. 
	 * @param keyCode The keycode belonging to the package. 
	 * @param file A FileManager object representing the file to encrypt and upload. This can not be a folder of files on the local file system.
	 * @returnType File
	 * @return A File object with meta data about the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFile(String packageId, String keyCode, FileManager file) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, keyCode, file);
	}
	
	/**
	 * @description Encrypt and upload a new file. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The unique package id of the package for the file upload operation.  
	 * @param keyCode The keycode belonging to the package. 
	 * @param file A FileManager object representing the file to encrypt and upload. This is a single file on your local file system, and can not be a folder.
	 * @param progress A progress callback object which can be used to report back progress on how the upload is progressing.
	 * @returnType File
	 * @return A File object with meta data about the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFile(String packageId, String keyCode, FileManager file, ProgressInterface progress) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, keyCode, file, progress);
	}
	
	/**
	 * @description Deletes a temporary package, which is a package that has not yet been finalized.
	 * @param packageId The unique package id of the package to be deleted. 
	 * @throws DeletePackageException
	 */
	public void deleteTempPackage(String packageId) throws DeletePackageException
	{
		DeleteTempPackageHandler handler = (DeleteTempPackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.DELETE_TEMP_PACKAGE);
		handler.makeRequest(packageId);
	}
	
	/**
	 * @description Encrypt and upload a new file. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The packageId to attach the file to. 
	 * @param keyCode The keycode belonging to the package.
	 * @param serverSecret The serverSecret belonging to the package. 
	 * @param file The given file to encrypt and upload. This can not be a folder.
	 * @param progress A progress callback object which can be used to report back progress on how the upload is progressing.
	 * @returnType File
	 * @return A File object with metadata for the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 * @deprecated deprecated method
	 */
	public File encryptAndUploadFile(String packageId, String keyCode, String serverSecret, FileManager file, ProgressInterface progress) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, null, keyCode, serverSecret, file, progress);
	}
	
	/**
	 * @description Retrieves a list of all active packages received for the given API User.
	 * @return A List<PackageReference> containing package metadata for all received packages.
	 * @returnType List<PackageReference>
	 * @throws GetPackagesException
	 */
	public List<PackageReference> getReceivedPackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.RECEIVED_PACKAGES);
		return handler.makeRequest();
	}
					
	/**
	 * @description Encrypt and upload a new file to a directory in a Workspace package. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The unique package id of the package for the file upload operation.  
	 * @param directoryId The unique directory id of the directory for the file upload operation.  
	 * @param keyCode The keycode belonging to the package.
	 * @param file A FileManager object representing the file to encrypt and upload. This is a single file on your local file system, and can not be a folder.
	 * @returnType File
	 * @return A File object with meta data about the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFileInDirectory(String packageId, String directoryId, String keyCode, FileManager file) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, directoryId, keyCode, file);
	}
	
	/**
	 * @description Encrypt and upload a new file to a directory in a Workspace package. The file will be encrypted before being uploaded to the server. the function will block until the file is uploaded.
	 * @param packageId The unique package id of the package for the file upload operation.  
	 * @param directoryId The unique directory id of the directory for the file upload operation.  
	 * @param keyCode The keycode belonging to the package.
	 * @param file A FileManager object representing the file to encrypt and upload. This is a single file on your local file system, and can not be a folder.
	 * @param progress A progress callback object which can be used to report back progress on how the upload is progressing.
	 * @returnType File
	 * @return A File object with meta data about the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFileInDirectory(String packageId, String directoryId, String keyCode, FileManager file, ProgressInterface progress) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, directoryId, keyCode, file, progress);
	}
	
	/**
	 * @description Encrypt and upload a message. The message will be encrypted before being uploaded to the server.
	 * @param packageId The unique package id of the package for the message upload operation.
	 * @param keyCode The keycode belonging to the package. 
	 * @param message The message string to encrypt and upload.
	 * @throws MessageException 
	 */
	public void encryptAndUploadMessage(String packageId, String keyCode, String message) throws MessageException
	{
		new AddMessageHandler(uploadManager, new AddMessageRequest(uploadManager.getJsonManager()));
		AddMessageHandler handler = new AddMessageHandler(uploadManager, new AddMessageRequest(uploadManager.getJsonManager()));
		handler.makeRequest(packageId, keyCode, message);
	}
	
	/**
	 * @description Finalizes the package so it can be delivered to the recipients.
	 * @param packageId The unique package id of the package to be finalized.
	 * @param keycode The keycode belonging to the package.
	 * @returnType PackageURL
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
	
	/**
	 * @description Finalizes the package so it can be delivered to the recipients.
	 * @param packageId The unique package id of the package to be finalized.
	 * @param keycode The keycode belonging to the package.
	 * @param notify A boolean flag indicating whether SendSafely should send the secure link to the package recipients
	 * @returnType PackageURL
	 * @return A link to access the package. This link can be sent to the recipients.
	 * @throws LimitExceededException
	 * @throws FinalizePackageFailedException
	 * @throws ApproverRequiredException 
	 */
	public PackageURL finalizePackage(String packageId, String keycode, boolean notify) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		FinalizePackageHandler handler = (FinalizePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FINALIZE_PACKAGE);
		handler.setNotify(notify);
		return handler.makeRequest(packageId, keycode);
	}
	
	/**
	 * @description Finalizes the package so it can be delivered to the recipients.
	 * @param packageId The packageId which is to be finalized.
	 * @param packageCode The packageCode belonging to the package. 
	 * @param keycode The keycode belonging to the package.
	 * @returnType PackageUrl
	 * @return A PackageUrl link to access the package. This link can be sent to the recipients.
	 * @throws LimitExceededException
	 * @throws FinalizePackageFailedException
	 * @throws ApproverRequiredException
	 * @deprecated  deprecated method
	 */
	public PackageURL finalizePackage(String packageId, String packageCode, String keycode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException
	{
		FinalizePackageHandler handler = (FinalizePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FINALIZE_PACKAGE);
		return handler.makeRequest(packageId, keycode);
	}
	
	/**
	 * @description Finalizes an undisclosed package, which is a package without recipients. Anyone with access to the link can access the package. 
	 * @param packageId The unique package id of the package to be finalized.
	 * @param keycode The keycode belonging to the package.
	 * @returnType PackageUrl
	 * @return A PackageUrl link to access the package. This link can be sent to the recipients.
	 * @throws LimitExceededException
	 * @throws FinalizePackageFailedException
	 * @throws ApproverRequiredException 
	 */
	public PackageURL finalizeUndisclosedPackage(String packageId, String keycode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException
	{
		FinalizePackageHandler handler = (FinalizePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FINALIZE_PACKAGE);
		return handler.makeRequest(packageId, keycode, true);
	}
	
	/**
	 * @description Finalizes an undisclosed package, which is a package without recipients, and protects it with a password. Anyone with access to the link will also be required to supply the password to access the package. 
	 * @param packageId The unique package id of the package to be finalized.
	 * @param password A password that will be required to access the contents of the package.
	 * @param keycode The keycode belonging to the package.
	 * @returnType PackageUrl
	 * @return A PackageUrl link to access the package. This link can be sent to the recipients.
	 * @throws LimitExceededException
	 * @throws FinalizePackageFailedException
	 * @throws ApproverRequiredException 
	 */
	public PackageURL finalizeUndisclosedPackage(String packageId, String password, String keycode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException
	{
		FinalizePackageHandler handler = (FinalizePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FINALIZE_PACKAGE);
		return handler.makeRequest(packageId, keycode, true, password);
	}
	
	/**
	 * @description Generates a new SendSafely API key and secret for the provided SendSafely user name and password. This key and secret can be used to authenticate to the SendSafely API. If the current user has Two-Step Authentication enabled, a TwoFactorAuthException exception will be returned to the client that includes a validation token. Additionally, a verification code will be sent via SMS message to the user's mobile number. Both the validation token and verification code will be needed in a follow-up call to the generateKey2FA API method in order to complete the authentication process and receive an API key and secret.  
	 * @param host The hostname you use to access SendSafely. Should be https://companyname.sendsafely.com or https://www.sendsafely.com
	 * @param email Email address of the user required for authenticating to SendSafely. 
	 * @param password Password of the user required for authenticating to SendSafely.
	 * @param keyDescription User defined description of the generated API key.
	 * @returnType DefaultCredentials
	 * @return A DefaultCredentials object containing the api key.
	 * @throws TwoFactorAuthException 
	 * @throws InvalidCredentialsException 
	 * @throws EnterpriseInfoFailedException 
	 */
	public DefaultCredentials generateAPIKey(String host, String email, String password, String keyDescription) throws TwoFactorAuthException, InvalidCredentialsException, EnterpriseInfoFailedException
	{
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		String targetURL = connection.getHost()+"/auth-api/generate-key/";
		String urlParameters = "{email:'"+email+"', password:'"+password+"', keyDescription:'"+keyDescription+"'}";
		String result = executeInternalPostHttpSend(targetURL, urlParameters, "PUT");
		Map gsonJavaObj = new Gson().fromJson(result, Map.class);
		String apiKey = gsonJavaObj.get("apiKey")==null?"":gsonJavaObj.get("apiKey").toString();
		String apiSecret = gsonJavaObj.get("apiSecret")==null?"":gsonJavaObj.get("apiSecret").toString();
		DefaultCredentials defaultCredentials = new DefaultCredentials(apiKey, apiSecret);
		Object response = gsonJavaObj.get("response");
		Object message = gsonJavaObj.get("message");
		if (response.equals(APIResponse.TWO_FA_REQUIRED.toString()))
        {
            throw new TwoFactorAuthException(response.toString(), message.toString());
        }
        else if (response.equals(APIResponse.AUTHENTICATION_FAILED.toString()))
        {
            throw new InvalidCredentialsException(message.toString());
        }
        else if (!response.equals(APIResponse.SUCCESS.toString()))
        {
            throw new EnterpriseInfoFailedException(message.toString());
        }
		return defaultCredentials;
	}
	
	/**
	 * @description Generates a new SendSafely API key and secret for a user that has Two-Step Authentication enabled. This key and secret can be used to authenticate to the SendSafely API.  
	 * @param host The hostname you use to access SendSafely. Should be https://companyname.sendsafely.com or https://www.sendsafely.com
	 * @param validationToken The validation token returned from a call to generateAPIKey() by a Two-Step Authentication enabled user. 
	 * @param smsCode The SMS verification code received from a call to generateAPIKey() by a Two-Step Authentication enabled user. 
	 * @param keyDescription User defined description of the generated API key.
	 * @returnType DefaultCredentials
	 * @return A DefaultCredentials object containing the key.
	 * @throws InvalidCredentialsException
	 * @throws EnterpriseInfoFailedException
	 */
	public DefaultCredentials generateKey2FA(String host, String validationToken, String smsCode, String keyDescription) throws InvalidCredentialsException, EnterpriseInfoFailedException {
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		String targetURL = connection.getHost()+"/auth-api/generate-key/"+validationToken+"/";
		String urlParameters = "{smsCode:'"+smsCode+"', keyDescription:'"+keyDescription+"'}";
		String result = executeInternalPostHttpSend(targetURL, urlParameters, "POST");
		Map gsonJavaObj = new Gson().fromJson(result, Map.class);
		String apiKey = gsonJavaObj.get("apiKey").toString();
		String apiSecret = gsonJavaObj.get("apiSecret").toString();
		String response = gsonJavaObj.get("response").toString();
		DefaultCredentials defaultCredentials = new DefaultCredentials(apiKey, apiSecret);
		if (response.equals(APIResponse.AUTHENTICATION_FAILED.toString()))
        {
            throw new InvalidCredentialsException("Error on credentials");
        }
        else if (!response.equals(APIResponse.SUCCESS.toString()))
        {
            throw new EnterpriseInfoFailedException("Error on credentials");
        }
		return defaultCredentials;
	}
	
	/**
	 * @description Get all active packages for the current user.
	 * @returnType List<PackageReference>
	 * @return A List<PackageReference> of all active package IDs.
	 * @throws GetPackagesException
	 */
	public List<PackageReference> getActivePackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ACTIVE_PACKAGES);
		return handler.makeRequest();
	}
	
	/**
	 * @description Retrieves activity log records for a Workspace package. The method supports returning up to 10 records at a time. The caller must be the owner of the Workspace, assigned to the manager role within the Workspace, or an enterprise administrator.
	 * @param packageId The unique package id of the Workspace package for the get activity log operation.
	 * @param rowIndex Integer representing the index of the first activity log record to retrieve.  
	 * @returnType List<ActivityLogEntry>
	 * @return List<ActivityLogEntry> object containing the activity log entries.
	 * @throws GetActivityLogException
	 */
	public List<ActivityLogEntry> getActivityLog(String packageId, int rowIndex) throws GetActivityLogException {
		GetActivityLogHandler handler = ((GetActivityLogHandler)HandlerFactory.getInstance(uploadManager, Endpoint.GET_ACTIVITY_LOG));
		return handler.makeRequest(packageId, rowIndex);
	}
	
	/**
	 * @description Get all archived packages for the current user.
	 * @returnType List<PackageReference>
	 * @return A List<PackageReference> of all archived package IDs.
	 * @throws GetPackagesException
	 */
	public List<PackageReference> getArchivedPackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ARCHIVED_PACKAGES);
		return handler.makeRequest();
	}
	
	/**
	 * @description Retrieve the list of available Contact Groups for the current user profile, including all email addresses associated with each Contact Group. 
	 * @returnType List<ContactGroup>
	 * @return List<ContactGroup> object of Contact Groups and email addresses.
	 * @throws GetContactGroupsFailedException
	 */
	public List<ContactGroup> getContactGroups() throws GetContactGroupsFailedException{
		GetContactGroupsHandler handler = new GetContactGroupsHandler(uploadManager, new GetContactGroupsRequest(uploadManager.getJsonManager()));
		return handler.makeRequest();
	}
	
	/**
	 * @description Retrieves meta data about a directory in a Workspace package.
	 * @param packageId The unique package id of the package for the target directory.
	 * @param directoryId The unique directory id of the target directory. 
	 * @returnType Directory
	 * @return A Directory object containing information about the directory.
	 * @throws DirectoryOperationFailedException
	 */
	public Directory getDirectory(String packageId, String directoryId) throws DirectoryOperationFailedException{
		GetDirectoryHandler handler = ((GetDirectoryHandler)HandlerFactory.getInstance(uploadManager, Endpoint.GET_DIRECTORY));
		return handler.makeRequest(packageId, directoryId);
	}
	
	/**
	 * @description Gets all recipients assigned to the current user's Dropzone.
	 * @returnType List<String>
	 * @return A List<String> of email addresses that are Dropzone recipients.
	 * @throws DropzoneRecipientFailedException
	 */
	public List<String> getDropzoneRecipient() throws DropzoneRecipientFailedException
	{
		GetDropzoneRecipientHandler handler = new GetDropzoneRecipientHandler(uploadManager, new GetDropzoneRecipientRequest(uploadManager.getJsonManager()));
		return handler.makeRequest();
	}
	
	/**
	 * @description Retrieve the list of available Contact Groups in the user's organization, including all email addresses associated with each Contact Group.
	 * @returnType List<ContactGroup>
	 * @return List<ContactGroup> object of Contact Groups and email addresses.
	 * @throws GetContactGroupsFailedException
	 */
	public List<ContactGroup> getEnterpriseContactGroups() throws GetContactGroupsFailedException{
		GetContactGroupsHandler handler = new GetContactGroupsHandler(uploadManager, new GetOrganizationContactGroupsRequest(uploadManager.getJsonManager()));
		return handler.makeRequest();
	}
	
	/**
	 * @description Retrieves information about the organization the user belongs to. 
	 * @returnType EnterpriseInfo
	 * @return An EnterpriseInfo object.
	 * @throws EnterpriseInfoFailedException
	 */
	public EnterpriseInfo getEnterpriseInfo() throws EnterpriseInfoFailedException
	{
		EnterpriseInfoHandler handler = (EnterpriseInfoHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ENTERPRISE_INFO);
		return handler.makeRequest();
	}
	
	/**
	 * @description Generates a new RSA Key pair used to encrypt keycodes. The private key as well as an identifier associating the public Key is returned to the user. The public key is uploaded and stored on the SendSafely servers. 
	 * @param description The description used for generating the key pair.
	 * @returnType Privatekey
	 * @return Returns a Private Key containing the armored private key and a Public Key ID associating a public key to the private key.
	 * @throws NoSuchAlgorithmException 
	 * @throws PublicKeysFailedException 
	 * @throws IOException 
	 * @throws PGPException 
	 */
	public Privatekey generateKeyPair(String description) throws NoSuchAlgorithmException, PublicKeysFailedException, PGPException, IOException{

		UploadPublicKey handler = new UploadPublicKey(uploadManager);
		Keypair kp = CryptoUtil.GenerateKeyPair();
		String publicKeyId = handler.makeRequest(kp.getPublicKey(), description);
		Privatekey privateKey = new Privatekey();
		privateKey.setPublicKeyId(publicKeyId);
		privateKey.setArmoredKey(kp.getPrivateKey());
		return privateKey;
	}
	
	/**
	 * @description Downloads and decrypts a keycode from the server for a given packageId and RSA Key pair.
	 * @param packageId The package id for the keycode.
	 * @param privateKey The private key associated with the RSA Key pair used to encrypt the package keycode.
	 * @return Returns the decrypted keycode.
	 * @returnType String
	 * @throws GetKeycodeFailedException
	 */
	public String getKeycode(String packageId, Privatekey privateKey) throws GetKeycodeFailedException{
		GetKeycode handler = new GetKeycode(uploadManager);
		return handler.get(packageId, privateKey);
	}
	
	/**
	 * @description Revokes a public key from the server. Only call this if the private key has been deleted and should not be used anymore.
	 * @param publicKeyId The public key id to revoke.
	 * @throws PublicKeysFailedException 
	 */
	public void revokePublicKey(String publicKeyId) throws PublicKeysFailedException{
		RevokePublicKey handler = new RevokePublicKey(uploadManager);
		handler.makeRequest(publicKeyId);
	}


	/**
	 * @description Retrieves meta data about a file in a Workspace package.
	 * @param packageId The unique package id of the package for the get file information operation.
	 * @param directoryId The unique directory id of the directory containing the target file.
	 * @param fileId The unique file id of the target file.
	 * @returnType FileInfo
	 * @return A FileInfo object containing information about the file.
	 * @throws FileOperationFailedException 
	 */
	public FileInfo getFileInformation(String packageId, String fileId, String directoryId) throws FileOperationFailedException{
		FileInformationHandler handler = (FileInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FILE_INFORMATION);
		return handler.makeRequest(packageId, fileId, directoryId);
	}
	
	/**
	 * @description Returns packages in the current user's organization based on provided search criteria. The search defaults to returning all packages up to the current date and time, if a specific value is not passed for each search criteria. A maximum of 100 records will be returned per method call. The calling user must be a SendSafely Enterprise Administrator. 
	 * @param fromDate Date and time to search for packages with a package timestamp that is greater than or equal to the specified value. 
	 * @param toDate Date and time to search for packages with a package timestamp that is less than or equal to the specified value. 
	 * @param sender Email address to search for packages with a matching package sender email address. A valid email address must be provided.
	 * @param status PackageStatus enum value to search for packages with a matching package status.
	 * @param recipient Email address to search for packages with a matching recipient email address. A valid email address must be provided.
	 * @param fileName Name of a file to search for packages with a matching file name.
	 * @returnType PackageSearchResults
	 * @return A PackageSearchResults object.
	 * @throws GetPackagesException
	 */
	public PackageSearchResults getOrganizationPackages(Date fromDate, Date toDate, String sender, PackageStatus status, String recipient, String fileName) throws GetPackagesException
	{
		GetOrganizationPackagesHandler handler = (GetOrganizationPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ORGANIZATION_PACKAGES);
		return handler.makeRequest(fromDate, toDate, sender, status, recipient, fileName);
	}
	
	/**
	 * @description Fetch the latest package meta data about a specific package given the unique package id.
	 * @param packageId The unique package id of the package for the get package information operation.
	 * @returnType Package
	 * @return A Package object containing package information.
	 * @throws PackageInformationFailedException
	 */
	public Package getPackageInformation(String packageId) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequest(packageId);
	}
	
	/**
	 * @description Fetch the latest package meta data about a specific package given a secure link of type String.
	 * @param link String representing the secure link for which package information is to be fetched.
	 * @returnType Package
	 * @return A Package object containing package information.
	 * @throws PackageInformationFailedException
	 */
	public Package getPackageInformationFromLink(String link) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequestFromLink(link);
	}
	
	/**
	 * @description Fetch the latest package meta data about a specific package given the secure link of type java.net.URL.
	 * @param link java.net.URL object representing the secure link for which package information is to be fetched.
	 * @returnType Package
	 * @return A Package object containing package information.
	 * @throws PackageInformationFailedException
	 */
	public Package getPackageInformationFromLink(URL link) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequestFromLink(link);
	}
	
	/**
	 * @description Returns a secure link for accessing a package. This method is intended for generating a shareable link for a Workspace package, however non-Workspace packages are also supported. Packages with a temporary or deleted PackageState are not supported by this method.    
	 * @param packageId The unique package id of the package to generate the secure link.
	 * @param keyCode The keycode belonging to the package.
	 * @returnType String
	 * @return The secure link to access the package.
	 * @throws PackageInformationFailedException
	 */
	public String getPackageLink(String packageId, String keyCode) throws PackageInformationFailedException{
		Package p = getPackageInformation(packageId);
		if (p.getState() == PackageState.PACKAGE_STATE_TEMP || p.getState() == PackageState.PACKAGE_STATE_DELETED_COMPLETE || p.getState() == PackageState.PACKAGE_STATE_DELETED_INCOMPLETE || p.getState() == PackageState.PACKAGE_STATE_DELETED_PARTIALLY_COMPLETE )
				throw new PackageInformationFailedException("Package link could not be generated due to unsupported package state"); 
		return ((DefaultUploadManager)this.uploadManager).getApiHost() + "/receive/?packageCode=" + p.getPackageCode() + "#keycode=" + keyCode ;
	}
	
	/**
	 * @description Downloads a message from the specified secure link and decrypts it.
	 * @param secureLink String representing the secure link for which the message is to be downloaded.
	 * @returnType String
	 *  @return The decrypted message.
	 * @throws MessageException 
	 */
	public String getPackageMessage(String secureLink) throws MessageException
	{
		GetMessageHandler handler = new GetMessageHandler(uploadManager, new GetMessageRequest(uploadManager.getJsonManager()));
		return handler.makeRequest(secureLink);
	}
	
	/**
	 * @description Gets a recipient from a given package.
	 * @param packageId The unique package id of the package for the get recipient operation.
	 * @param recipientId The unique recipient id of the recipient to be retrieved.
	 * @returnType Recipient
	 * @return A Recipient object containing information about the recipient.
	 * @throws LimitExceededException
	 * @throws RecipientFailedException
	 */
	public Recipient getRecipient(String packageId, String recipientId) throws LimitExceededException, RecipientFailedException
	{
		GetRecipientHandler handler = new GetRecipientHandler(uploadManager);
		return handler.makeRequest(packageId, recipientId);
	}
	
	/**
	 * @description Gets information about the current logged in user.
	 * @returnType UserInformation
	 * @return A UserInformation object.
	 * @throws UserInformationFailedException
	 */
	public UserInformation getUserInformation() throws UserInformationFailedException
	{
		UserInformationHandler handler = (UserInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.USER_INFORMATION);
		return handler.makeRequest();
	}
	
	/**
	 * @description Moves a directory to the specified destination directory in a Workspace package.
	 * @param packageId The unique package id of the package for the move directory operation. 
	 * @param sourceDirectoryId The unique directory id of the directory to move.
	 * @param targetDirectoryId The unique directory id of the destination directory.
	 * @throws DirectoryOperationFailedException
	 */
	public void moveDirectory(String packageId, String sourceDirectoryId, String targetDirectoryId) throws DirectoryOperationFailedException
	{
		MoveDirectoryHandler handler = (MoveDirectoryHandler)HandlerFactory.getInstance(uploadManager, Endpoint.MOVE_DIRECTORY);
		handler.makeRequest(packageId, sourceDirectoryId, targetDirectoryId);
	}
	
	/**
	 * @description Moves a file to the specified destination directory in a Workspace package.
	 * @param packageId The unique package id of the package for the move file operation.
	 * @param fileId The unique file id of the file to move.
	 * @param targetDirectoryId The unique directory id of the destination directory.
	 * @throws FileOperationFailedException
	 */
	public void moveFile(String packageId, String fileId, String targetDirectoryId) throws FileOperationFailedException
	{
		MoveFileHandler handler = (MoveFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.MOVE_FILE);
		handler.makeRequest(packageId, fileId, targetDirectoryId);
	}

	/**
	 * @description Parses out SendSafely links from a String of text.
	 * @param text The text to parse out links from.
	 * @returnType List<String>
	 * @return A List<String> of SendSafely URLs
	 */
	public List<String> parseLinksFromText(String text) {
		ParseLinksHandler handler = new ParseLinksHandler();
		return handler.parse(text);
	}

	/**
	 * @description Remove a Contact Group from a package.
	 * @param packageId The unique package id of the package for the remove the Contact Group operation. 
	 * @param groupId The unique id of the Contact Group to remove from the package.
	 * @throws RemoveContactGroupAsRecipientFailedException
	 */
	public void removeContactGroupFromPackage(String packageId,String groupId) throws RemoveContactGroupAsRecipientFailedException{
		RemoveContactGroupAsRecipientHandler handler = new RemoveContactGroupAsRecipientHandler(uploadManager, new RemoveContactGroupAsRecipientRequest(uploadManager.getJsonManager(), packageId, groupId));
		handler.makeRequest();
	}
	
	/**
	 * @description Deletes a recipient email address from the current user's Dropzone.
	 * @param email The recipient email address to delete from the Dropzone.
	 * @throws DropzoneRecipientFailedException
	 */
	public void removeDropzoneRecipient(String email) throws DropzoneRecipientFailedException
	{
		RemoveDropzoneRecipientHandler handler = new RemoveDropzoneRecipientHandler(uploadManager);
		handler.makeRequest(email);
	}
	
	/**
	 * @description Removes a recipient from a given package.
	 * @param packageId The unique package id of the package for the remove recipient operation.
	 * @param recipientId The unique recipient id of the recipient to remove from the package.
	 * @throws RecipientFailedException
	 */
	public void removeRecipient(String packageId, String recipientId) throws RecipientFailedException
	{
		RemoveRecipientHandler handler = new RemoveRecipientHandler(uploadManager);
		handler.makeRequest(packageId, recipientId);
	}
	
	/**
	 * @description Remove user email address from the specified Contact Group.
	 * @param groupId The unique id of the Contact Group for the remove user operation.
	 * @param userId The unique id of the user whose email address is to be removed from the Contact Group.
	 * @throws RemoveEmailContactGroupFailedException
	 */
	public void removeUserFromContactGroup(String groupId, String userId) throws RemoveEmailContactGroupFailedException{
		RemoveUserContactGroupHandler handler = new RemoveUserContactGroupHandler(uploadManager, new RemoveUserFromContactGroupRequest(uploadManager.getJsonManager(), groupId, userId));
		handler.makeRequest();
	}
	
	/**
	* @description Renames a directory to the specified directory name in a Workspace package.
	 * @param packageId The unique package id of the package for the rename directory operation.
	 * @param directoryId The unique directory id of the directory to rename.
	 * @param directoryName The new name of the directory.
	 * @throws DirectoryOperationFailedException
	 */
	public void renameDirectory(String packageId, String directoryId, String directoryName) throws DirectoryOperationFailedException
	{
		UpdateDirectoryNameHandler handler = (UpdateDirectoryNameHandler)HandlerFactory.getInstance(uploadManager, Endpoint.DIRECTORY_NAME);
		handler.makeRequest(packageId, directoryId, directoryName);
	}
	
	/**
	 * @description Updates the package descriptor. For a Workspaces package, this method can be used to change the name of the Workspace.
	 * @param packageId The unique package id of the package for the descriptor update operation.
	 * @param packageDescriptor The string value to update the package descriptor to. 
	 * @throws UpdatePackageDescriptorFailedException
	 */
	public void updatePackageDescriptor(String packageId, String packageDescriptor) throws UpdatePackageDescriptorFailedException
	{
		UpdatePackageDescriptorHandler handler = (UpdatePackageDescriptorHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_NAME);
		handler.makeRequest(packageId, packageDescriptor);
	}
	
	/**
	 * @description Update the package life. Setting the life to 0 means the package will not expire.
	 * @param packageId The unique package id of the package for the update package life operation.
	 * @param life The new package life. Setting this parameter to 0 means the package will not expire.
	 * @throws UpdatePackageLifeException
	 */
	public void updatePackageLife(String packageId, int life) throws UpdatePackageLifeException
	{
		UpdatePackageLifeHandler handler = (UpdatePackageLifeHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_LIFE);
		handler.makeRequest(packageId, life);
	}
	
	/**
	 * @description Used to update the role of a Workspace package recipient (i.e. Workspace collaborator).
	 * @param packageId The unique package id of the Workspace package for the update role operation.
	 * @param recipientId The unique recipient id for the Workspace collaborator whose role is to be updated. 
	 * @param role String representing the role for the update role operation. Supported values are VIEWER, CONTRIBUTOR, MANAGER, and OWNER.
	 * @throws UpdateRecipientFailedException
	 */
	public void updateRecipientRole(String packageId, String recipientId, String role) throws UpdateRecipientFailedException
	{
		UpdateRecipientHandler handler = (UpdateRecipientHandler)HandlerFactory.getInstance(uploadManager, Endpoint.UPDATE_RECIPIENT);
		handler.makeRequest(packageId, recipientId, role);
	}
	
	/**
	 * @description Verifies a user's API key and secret. This method is typically called when a new user uses the API for the first time. 
	 * @returnType String
	 * @return String containing the email belonging to the authenticated user.
	 * @throws InvalidCredentialsException
	 */
	public String verifyCredentials() throws InvalidCredentialsException
	{
		VerifyCredentialsHandler handler = (VerifyCredentialsHandler)HandlerFactory.getInstance(uploadManager, Endpoint.VerifyCredentials);
		return handler.verify();
	}

	/**
	 * @description Verifies the current version of the SendSafely API against the server.
	 * @returnType Version
	 * @return A Version object containing version information
	 * @throws SendFailedException
	 */
	public Version verifyVersion() throws SendFailedException
	{
		VerifyVersionHandler handler = (VerifyVersionHandler)HandlerFactory.getInstance(uploadManager, Endpoint.VerifyVersion);
		handler.setVersion(version);
		return handler.makeRequest();
	}
	
	/**
	 * @description Deprecated helper utility for the generation of a key for unauthenticated requests.
	 * @param targetURL The url of the sendsafely site used.
	 * @param urlParameters The parameters (normally in json format)
	 * @param method The http method
	 * @returnType String
	 * @return A string containing the key.
	 * @deprecated  deprecated method
	 */
	public static String executePostHttpSend(String targetURL, String urlParameters, String method) {
		return executeInternalPostHttpSend(targetURL, urlParameters, method);
		}
    /**
	 * HELPER UTILITY FOR GENERATE KEY FOR UNAUTHENTICATED REQUESTS.
	 * @param targetURL 
	 * @param urlParametersparameters (normally in json format)
	 * @param methodhttp method
	 * @returnType String
	 * @return A string response from the server
	 */
	private static String executeInternalPostHttpSend(String targetURL, String urlParameters, String method) {
		  HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod(method);
		    connection.setRequestProperty("Content-Type", 
		        "application/json");

		    connection.setRequestProperty("Content-Length", 
		        Integer.toString(urlParameters.getBytes().length));
		    connection.setRequestProperty("Content-Language", "en-US");  

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
}
