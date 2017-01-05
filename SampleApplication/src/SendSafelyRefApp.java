

import java.io.IOException;

import com.sendsafely.File.*;
import com.sendsafely.Package;
import com.sendsafely.*;
import com.sendsafely.dto.PackageURL;
import com.sendsafely.exceptions.*;
import com.sendsafely.file.DefaultFileManager;
import com.sendsafely.file.FileManager;

public class SendSafelyRefApp {
	
	public static void main(String[] args) throws SendFailedException, IOException, InvalidCredentialsException, CreatePackageFailedException, LimitExceededException, FinalizePackageFailedException, UploadFileException, ApproverRequiredException, RecipientFailedException, PackageInformationFailedException, DownloadFileException, PasswordRequiredException
	{
		/*
		 * This example will read in the following command line arguments:
		 *
		 * SendSafelyHost: The SendSafely hostname to connect to. Enterprise users should connect to their designated 
		 *	Enterprise host (company-name.sendsafely.com)
		 *
		 * UserApiKey: The API key for the user you want to connect to.  API Keys can be obtained from the Edit 
		 *  Profile screen when logged in to SendSafely
		 *
		 * UserApiSecret: The API Secret associated with the API Key used above.  The API Secret is provided to  
		 *  you when you generate a new API Key.  
		 *
		 * FileToUpload: Local path to the file you want to upload. 
		 *
		 * RecipientEmailAddress: The email address of the person you want to send the file to. 
		 */
		
		if (args == null || (args.length != 5))
		{
			// Invalid number of arguments.  Print the usage syntax to the screen and exit. 
			System.out.println("Usage: " + SendSafelyRefApp.class.getName() + " SendSafelyHost UserApiKey UserApiSecret FileToUpload RecipientEmailAddress");
			System.out.println("\nThis program will print out the secure URL to access the package after it has been submitted.");
            return;
		}
		else
		{
			// Valid arguments provided.  Assign each argument to a local variable 
            String sendSafelyHost = args[0];
            String userApiKey = args[1];
            String userApiSecret = args[2];
            String fileToUpload = args[3];
            String recipientEmailAddress = args[4];
            
            System.out.println("Initializing API");
            SendSafely sendSafely = new SendSafely(sendSafelyHost, userApiKey, userApiSecret);
            
            String userEmail = sendSafely.verifyCredentials();
            System.out.println("Connected to SendSafely as user: " + userEmail);
            
            // Create a new empty package
            Package pkgInfo = sendSafely.createPackage();
            
            String packageId = pkgInfo.getPackageId();
            System.out.println("Created new empty package with PackageID#" + packageId);
            
            Recipient newRecipient = sendSafely.addRecipient(packageId, recipientEmailAddress);
            System.out.println("Adding Recipient (Recipient ID#" + newRecipient.getRecipientId() + ")");
            
            System.out.println("Uploading File");
            FileManager uploadManager = new DefaultFileManager(new java.io.File(fileToUpload));
            File addedFile = sendSafely.encryptAndUploadFile(packageId, pkgInfo.getKeyCode(), uploadManager, new ProgressCallback());
            System.out.println("Upload Complete - File Id#" + addedFile.getFileId() + ".  Finalizing Package.");

            // Package is finished, call the finalize method to make the package available for pickup and print the URL for access.
            PackageURL packageLink = sendSafely.finalizePackage(packageId, pkgInfo.getKeyCode());
            System.out.println("Success: " + packageLink.getSecureLink());

            // Now download the file and save a copy	
            System.out.println("Downloading the file");
            Package pkgToDownload = sendSafely.getPackageInformationFromLink(packageLink.getSecureLink());
            for(File file : pkgToDownload.getFiles()) 
            {		
            	java.io.File downloadedFile = sendSafely.downloadFile(pkgToDownload.getPackageId(), file.getFileId(), pkgToDownload.getKeyCode(), new ProgressCallback());		
            	System.out.println("Downloaded File to path: " + downloadedFile.getAbsolutePath());		
            }
		}
	}
	
}
