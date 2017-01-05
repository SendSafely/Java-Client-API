package com.sendsafely;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.gson.Gson;
import com.sendsafely.connection.ConnectionFactory;
import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.credentials.CredentialsFactory;
import com.sendsafely.credentials.CredentialsManager;
import com.sendsafely.credentials.DefaultCredentials;
import com.sendsafely.dto.EnterpriseInfo;
import com.sendsafely.dto.PackageURL;
import com.sendsafely.dto.UserInformation;
import com.sendsafely.dto.request.AddDropzoneRecipientRequest;
import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.request.AddRecipientsRequest;
import com.sendsafely.dto.request.GetDropzoneRecipientRequest;
import com.sendsafely.dto.request.GetMessageRequest;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.CountryCode;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.Version;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.DropzoneRecipientFailedException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.exceptions.RecipientFailedException;
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
import com.sendsafely.exceptions.TwoFactorAuthException;
import com.sendsafely.exceptions.UpdatePackageLifeException;
import com.sendsafely.exceptions.UpdateRecipientFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.exceptions.UserInformationFailedException;
import com.sendsafely.file.FileManager;
import com.sendsafely.handlers.AddDropzoneRecipientHandler;
import com.sendsafely.handlers.AddFileHandler;
import com.sendsafely.handlers.AddMessageHandler;
import com.sendsafely.handlers.AddRecipientHandler;
import com.sendsafely.handlers.AddRecipientsHandler;
import com.sendsafely.handlers.CreatePackageHandler;
import com.sendsafely.handlers.DeletePackageHandler;
import com.sendsafely.handlers.DownloadAndDecryptFileHandler;
import com.sendsafely.handlers.EnterpriseInfoHandler;
import com.sendsafely.handlers.FinalizePackageHandler;
import com.sendsafely.handlers.GetDropzoneRecipientHandler;
import com.sendsafely.handlers.GetMessageHandler;
import com.sendsafely.handlers.GetPackagesHandler;
import com.sendsafely.handlers.GetRecipientHandler;
import com.sendsafely.handlers.HandlerFactory;
import com.sendsafely.handlers.PackageInformationHandler;
import com.sendsafely.handlers.ParseLinksHandler;
import com.sendsafely.handlers.RemoveDropzoneRecipientHandler;
import com.sendsafely.handlers.RemoveRecipientHandler;
import com.sendsafely.handlers.UpdatePackageLifeHandler;
import com.sendsafely.handlers.UpdateRecipientHandler;
import com.sendsafely.handlers.UserInformationHandler;
import com.sendsafely.handlers.VerifyCredentialsHandler;
import com.sendsafely.handlers.VerifyVersionHandler;
import com.sendsafely.json.JsonManager;
import com.sendsafely.upload.UploadFactory;
import com.sendsafely.upload.UploadManager;

/**
 * @author Erik Larsson
 * @description The main SendSafely API. Use this API to create packages and append files and recipients.
 */
public class SendSafely {

	private UploadManager uploadManager;
    private JsonManager jsonManager = null;
	
	protected double version = 0.3;
	
	/**
	 * @description The constructor to create a new SendSafely object.
	 * @param host The host name to connect to. Should be either https://www.sendsafely.com or https://www.sendsafely.co.uk
	 * @param apiKey The API key for the user. A new API key can be generated on the profile page for a user.
	 * @param apiSecret The secret belonging to the API key.
	 */
	public SendSafely(String host, String apiKey, String apiSecret)
	{
		Security.addProvider(new BouncyCastleProvider());
		CredentialsManager credentialsManager = CredentialsFactory.getDefaultCredentials(apiKey, apiSecret);
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		
		uploadManager = UploadFactory.getManager(connection, credentialsManager);
	}
	
	public SendSafely(String host, CredentialsManager credentialsManager)
	{
		Security.addProvider(new BouncyCastleProvider());
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
		Security.addProvider(new BouncyCastleProvider());
		CredentialsManager credentialsManager = CredentialsFactory.getDefaultCredentials(apiKey, apiSecret);
		this.uploadManager = UploadFactory.getManager(connectionManager, credentialsManager);
	}
	
	public SendSafely(String host, ConnectionManager connectionManager, CredentialsManager credentialsManager)
	{	
		Security.addProvider(new BouncyCastleProvider());
		this.uploadManager = UploadFactory.getManager(connectionManager, credentialsManager);
	}

    public SendSafely(UploadManager uploadManager)
    {
        this.uploadManager = uploadManager;
    }

    /**
     * needed this constructor for the creation of the send safely api 
     * which will enable us to start calling the login processes.
     * @param host
     */
	public SendSafely(String host) {
		// TODO Auto-generated constructor stub
		this(host, (ConnectionManager)null, (CredentialsManager)null);
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
	 * @description Parses out SendSafely links from a String of text
	 * @param text The text to parse out links from.
	 * @return A list of SendSafely URLs
	 */
	public List<String> parseLinksFromText(String text) {
		ParseLinksHandler handler = new ParseLinksHandler();
		return handler.parse(text);
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
	 * @return A {@link Package} object containing information about the package.
	 * @throws CreatePackageFailedException
	 * @throws LimitExceededException
	 */
	public Package createPackage() throws CreatePackageFailedException, LimitExceededException
	{
		CreatePackageHandler handler = (CreatePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_PACKAGE);
		return handler.makeRequest();
	}
		
	/**
	 * @description Creates a new package belonging to another user. This method can only be called by Enterprise Admins and can only target a user within the same organization. 
	 * @param email The email address of the user the package will belong to
	 * @return A {@link Package} object containing information about the package.
	 * @throws CreatePackageFailedException
	 * @throws LimitExceededException
	 */
	public Package createPackageForUser(String email) throws CreatePackageFailedException, LimitExceededException
	{
		CreatePackageHandler handler = (CreatePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.CREATE_PACKAGE);
		return handler.makeRequest(email);
	}
	
	/**
	 * @description Adds a recipient to a given package.
	 * @param packageId The unique packageId that you are adding the recipient to
	 * @param email The recipient email to be added
	 * @return A {@link Recipient} object containing information about the recipient.
	 * @throws LimitExceededException
	 * @throws RecipientFailedException
	 */
	public Recipient addRecipient(String packageId, String email) throws LimitExceededException, RecipientFailedException
	{
		AddRecipientHandler handler = new AddRecipientHandler(uploadManager, new AddRecipientRequest(uploadManager.getJsonManager(), email));
		return handler.addRecipient(packageId);
	}
	
	/**
	 * @description Adds a list of recipients to a given package.
	 * @param packageId The unique packageId that you are adding the recipient to
	 * @param emails The list of recipients you would like to add
	 * @return A {@link List<Recipient>} list containing information about the recipients.
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
	 * @description Gets a recipient from a given package
	 * @param packageId The unique packageId that you working on
	 * @param recipientId The recipientId to grab
	 * @return A {@link Recipient} object containing information about the recipient.
	 * @throws LimitExceededException
	 * @throws RecipientFailedException
	 */
	public Recipient getRecipient(String packageId, String recipientId) throws LimitExceededException, RecipientFailedException
	{
		GetRecipientHandler handler = new GetRecipientHandler(uploadManager);
		return handler.makeRequest(packageId, recipientId);
	}
	
	/**
	 * @description Removes a recipient from a given package
	 * @param packageId The unique packageId that you working on
	 * @param recipientId The recipientId to grab
	 * @throws RecipientFailedException
	 */
	public void removeRecipient(String packageId, String recipientId) throws RecipientFailedException
	{
		RemoveRecipientHandler handler = new RemoveRecipientHandler(uploadManager);
		handler.makeRequest(packageId, recipientId);
	}
	
	/**
	 * @description Updates the permissions for a given recipient
	 * @param packageId The unique packageId the recipient belongs to
	 * @param recipientId The recipientId of the recipient to be updated.
	 * @return A {@link Recipient} object containing information about the recipient.
	 * @throws UpdateRecipientFailedException
	 */
	/*public Recipient updateRecipientPermission(String packageId, String recipientId, boolean canAddMessages, boolean canAddFiles, boolean canAddRecipients) throws UpdateRecipientFailedException
	{
		UpdateRecipientPermissionsHandler handler = (UpdateRecipientPermissionsHandler)HandlerFactory.getInstance(uploadManager, Endpoint.UPDATE_RECIPIENT_PERMISSIONS);
		return handler.makeRequest(packageId, recipientId, canAddMessages, canAddFiles, canAddRecipients);
	}*/
	
	/**
	 * @description Adds a phone number to a given recipient.
	 * @param packageId The unique packageId that you are updating
	 * @param recipientId The recipientId to be updated
	 * @param phonenumber The phone number to associate with the recipient
	 * @param countryCode The country code that belongs to the phone number
	 * @throws UpdateRecipientFailedException
	 */
	public void addRecipientPhonenumber(String packageId, String recipientId, String phonenumber, CountryCode countryCode) throws UpdateRecipientFailedException
	{
		UpdateRecipientHandler handler = (UpdateRecipientHandler)HandlerFactory.getInstance(uploadManager, Endpoint.UPDATE_RECIPIENT);
		handler.makeRequest(packageId, recipientId, phonenumber, countryCode);
	}
	
	/**
	 * @description Adds a dropzone recipient.
	 * @param email The recipient email to be added
	 * @return A {@link String} string containing information about the recipient.
	 * @throws RecipientFailedException
	 */
	public void addDropzoneRecipient(String email) throws RecipientFailedException
	{
		AddDropzoneRecipientHandler handler = new AddDropzoneRecipientHandler(uploadManager, new AddDropzoneRecipientRequest(uploadManager.getJsonManager(), email));
		handler.addDropzoneRecipient(email);
	}
	
	/**
	 * @throws DropzoneRecipientFailedException 
	 * @description Get all dropzone recipients.
	 * @return List<String> a list of all dropzone recipients.
	 * @throws DropzoneRecipientFailedException
	 */
	public List<String> getDropzoneRecipient() throws DropzoneRecipientFailedException
	{
		GetDropzoneRecipientHandler handler = new GetDropzoneRecipientHandler(uploadManager, new GetDropzoneRecipientRequest(uploadManager.getJsonManager()));
		return handler.makeRequest();
	}
	
	/**
	 * @description Deletes a dropzone recipient for a given email address.
	 * @param email The unique email address that you want to delete within your dropzone list.
	 * @return 
	 * @throws DropzoneRecipientFailedException
	 */
	public void removeDropzoneRecipient(String email) throws DropzoneRecipientFailedException
	{
		RemoveDropzoneRecipientHandler handler = new RemoveDropzoneRecipientHandler(uploadManager);
		handler.makeRequest(email);
	}
	
	
	/**
	 * @description Deletes a package given a package ID.
	 * @param packageId The unique packageId that you want to delete.
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
	public List<PackageReference> getActivePackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ACTIVE_PACKAGES);
		return handler.makeRequest();
	}
	
	/**
	 * @description Get all archived packages
	 * @return List<String> a list of all archived package IDs.
	 * @throws GetPackagesException
	 */
	public List<PackageReference> getArchivedPackages() throws GetPackagesException
	{
		GetPackagesHandler handler = (GetPackagesHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ARCHIVED_PACKAGES);
		return handler.makeRequest();
	}
	
	/**
	 * @description Fetch the latest package meta data from the server and return it
	 * @param packageId The packageId to fetch information for
	 * @return {@link Package}
	 * @throws PackageInformationFailedException
	 */
	public Package getPackageInformation(String packageId) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequest(packageId);
	}
	
	/**
	 * @description Fetch the latest package meta data from the server and return it
	 * @param link The secure link to fetch information for
	 * @return {@link Package}
	 * @throws PackageInformationFailedException
	 */
	public Package getPackageInformationFromLink(String link) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequestFromLink(link);
	}
	
	/**
	 * @description Fetch the latest package meta data from the server and return it
	 * @param link The secure link to fetch information for
	 * @return {@link Package}
	 * @throws PackageInformationFailedException
	 */
	public Package getPackageInformationFromLink(URL link) throws PackageInformationFailedException
	{
		PackageInformationHandler handler = (PackageInformationHandler)HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION);
		return handler.makeRequestFromLink(link);
	}
	
	/**
	 * @description Update the package life. Setting the life to 0 means it will not expire
	 * @param packageId The packageId to fetch information for
	 * @param life The new package life
	 * @return {@link Package}
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
	public File encryptAndUploadFile(String packageId, String keyCode, FileManager file) throws LimitExceededException, UploadFileException
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
	public File encryptAndUploadFile(String packageId, String keyCode, FileManager file, ProgressInterface progress) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, keyCode, file, progress);
	}
	
	/**
	 * @description Encrypt and upload a new file. The file will be encrypted before being uploaded to the server. The function will block until the file is uploaded.
	 * @param packageId The packageId to attach the file to. 
	 * @param keyCode The keycode belonging to the package.
	 * @param serverSecret The serverSecret belonging to the package. 
	 * @param file The given file to encrypt and upload. This can not be a folder.
	 * @param progress A progress callback object which can be used to report back progress on how the upload is progressing.
	 * @return {@link File} a file object with metadata for the file.
	 * @throws LimitExceededException
	 * @throws UploadFileException
	 */
	public File encryptAndUploadFile(String packageId, String keyCode, String serverSecret, FileManager file, ProgressInterface progress) throws LimitExceededException, UploadFileException
	{
		AddFileHandler handler = (AddFileHandler)HandlerFactory.getInstance(uploadManager, Endpoint.ADD_FILE);
		return handler.makeRequest(packageId, keyCode, serverSecret, file, progress);
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
		new AddMessageHandler(uploadManager, new AddMessageRequest(uploadManager.getJsonManager()));
		AddMessageHandler handler = new AddMessageHandler(uploadManager, new AddMessageRequest(uploadManager.getJsonManager()));
		handler.makeRequest(packageId, keyCode, message);
	}
	
	/**
	 * @description Downloads and decrypts a given file from the server.
	 * @param packageId The packageId to download a file from.
	 * @param fileId The fileId to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @return void
	 * @throws DownloadFileException 
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode) throws DownloadFileException, PasswordRequiredException
	{
		return downloadFile(packageId, fileId, keyCode, null, (String)null);
	}
	
	/**
	 * @description Downloads and decrypts a given file from the server.
	 * @param packageId The packageId to download a file from.
	 * @param fileId The fileId to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @param password The password used to get the file 
	 * @return void
	 * @throws DownloadFileException 
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode, String password) throws DownloadFileException, PasswordRequiredException
	{
		return downloadFile(packageId, fileId, keyCode, null, password);
	}
	
	/**
	 * @description Downloads and decrypts a given file from the server.
	 * @param packageId The packageId to download a file from.
	 * @param fileId The fileId to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @param progress An optional progress interface to keep track of the download progress.
	 * @return a file object containing a temporary file name. The file must be renamed to be usable through any program using this function.
	 * @throws DownloadFileException 
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode, ProgressInterface progress) throws DownloadFileException, PasswordRequiredException
	{
		DownloadAndDecryptFileHandler handler = new DownloadAndDecryptFileHandler(uploadManager);
		return handler.makeRequest(packageId, fileId, keyCode, progress, null);
	}
	
	/**
	 * @description Downloads and decrypts a given file from the server.
	 * @param packageId The packageId to download a file from.
	 * @param fileId The fileId to download.
	 * @param keyCode The keycode belonging to the package. 
	 * @param progress An optional progress interface to keep track of the download progress.
	 * @param password The password required to get the file
	 * @return void
	 * @throws DownloadFileException 
	 */
	public java.io.File downloadFile(String packageId, String fileId, String keyCode, ProgressInterface progress, String password) throws DownloadFileException, PasswordRequiredException
	{
		DownloadAndDecryptFileHandler handler = new DownloadAndDecryptFileHandler(uploadManager);
		return handler.makeRequest(packageId, fileId, keyCode, progress, password);
	}
	
	/**
	 * @description Downloads a message from the given secure link
	 * @param secureLink The secure link to grab the message from. 
	 * @return The decrypted message
	 * @throws MessageException 
	 */
	public String getPackageMessage(String secureLink) throws MessageException
	{
		GetMessageHandler handler = new GetMessageHandler(uploadManager, new GetMessageRequest(uploadManager.getJsonManager()));
		return handler.makeRequest(secureLink);
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
	
	/**
	 * @description Finalizes the package so it can be delivered to the recipients
	 * @param packageId The packageId which is to be finalized.
	 * @param packageCode The packageCode belonging to the package. 
	 * @param keycode The keycode belonging to the package.
	 * @return A link to access the package. This link can be sent to the recipients.
	 * @throws LimitExceededException
	 * @throws FinalizePackageFailedException
	 * @throws ApproverRequiredException 
	 */
	public PackageURL finalizePackage(String packageId, String packageCode, String keycode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException
	{
		FinalizePackageHandler handler = (FinalizePackageHandler)HandlerFactory.getInstance(uploadManager, Endpoint.FINALIZE_PACKAGE);
		return handler.makeRequest(packageId, keycode);
	}
	
	/**
	 * @description Finalizes a package without recipients. Anyone with a link can access the package.
	 * @param packageId The packageId which is to be finalized.
	 * @param password A password that will be required before the recipient can access the files.
	 * @param keycode The keycode belonging to the package.
	 * @return A link to access the package. This link can be sent to the recipients.
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
	 * @description Finalizes a package without recipients. Anyone with a link can access the package.
	 * @param packageId The packageId which is to be finalized.
	 * @param keycode The keycode belonging to the package.
	 * @return A link to access the package. This link can be sent to the recipients.
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
	 * @description Starts the process to login with a username and password. Will
	 * throw an exception if two factor authentication is required which will force the caller
	 * to handle asking for more input and call its sister method to complete the second part of the
	 * login verification
	 * @param host - host name.
	 * @param email The recipient email to be added
	 * @param password - users password
	 * @param keyDescription - which for now is hard coded by the caller... typically "SendSafely CLI Key (auto generated)"
	 * @return A {@link String} string containing information about the recipient.
	 * @throws Exception 
	 * @throws RecipientFailedException
	 */
	public DefaultCredentials generateAPIKey(String host, String email, String password, String keyDescription) throws Exception
	{
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		String targetURL = connection.getHost()+"/auth-api/generate-key/";
		String urlParameters = "{email:'"+email+"', password:'"+password+"', keyDescription:'"+keyDescription+"'}";
		String result = executePostHttpSend(targetURL, urlParameters, "PUT");
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
	 * HELPER UTILITY FOR GENERATE KEY FOR UNAUTHENTICATED REQUESTS.
	 * @param targetURL 
	 * @param urlParameters - parameters (normally in json format)
	 * @param method - http method
	 * @return
	 */
	public static String executePostHttpSend(String targetURL, String urlParameters, String method) {
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

	/**
	 * This will accept a host, validation token, input(sms verification code), and a key description
	 * and will complete the login verification process.
	 * @param host
	 * @param validationToken
	 * @param input
	 * @param keyDescription
	 * @return
	 * @throws InvalidCredentialsException
	 * @throws EnterpriseInfoFailedException
	 */
	public DefaultCredentials generateKey2FA(String host, String validationToken, String input, String keyDescription) throws InvalidCredentialsException, EnterpriseInfoFailedException {
		ConnectionManager connection = ConnectionFactory.getDefaultManager(host);
		String targetURL = connection.getHost()+"/auth-api/generate-key/"+validationToken+"/";
		String urlParameters = "{smsCode:'"+input+"', keyDescription:'"+keyDescription+"'}";
		String result = executePostHttpSend(targetURL, urlParameters, "POST");
		Map gsonJavaObj = new Gson().fromJson(result, Map.class);
		String apiKey = gsonJavaObj.get("apiKey").toString();
		String apiSecret = gsonJavaObj.get("apiSecret").toString();
		String response = gsonJavaObj.get("response").toString();
		DefaultCredentials defaultCredentials = new DefaultCredentials(apiKey, apiSecret);
		if (response.equals(APIResponse.AUTHENTICATION_FAILED.toString()))
        {
            throw new InvalidCredentialsException("Error on credentials");
        }
//TODO: Consider adding a pinrefreshexception to mirror the .net api
        else if (!response.equals(APIResponse.SUCCESS.toString()))
        {
            throw new EnterpriseInfoFailedException("Error on credentials");
        }
		return defaultCredentials;
		
	}
	
}
