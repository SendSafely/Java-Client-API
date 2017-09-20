
package com.sendsafely.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.sendsafely.File;
import com.sendsafely.Package;
import com.sendsafely.SendSafely;
import com.sendsafely.exceptions.ApproverRequiredException;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.file.DefaultFileManager;
import com.sendsafely.file.FileManager;

public class Testing2 {

	@Test
	public void testAddDropzoneRecipient() throws FileNotFoundException, IOException {
		Properties configFile = new Properties();
		String host = null;
		String apiKey = null;
		String apiSecret = null;
		String email = null;
		try {
			configFile.load(Test.class.getClassLoader().getResourceAsStream("config.properties"));
			host = configFile.getProperty("host");
			apiKey = configFile.getProperty("apiKey");
			apiSecret = configFile.getProperty("apiSecret");
			email = configFile.getProperty("email");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		SendSafely sendSafely = new SendSafely(host, apiKey, apiSecret);
		try {
			String userEmail = sendSafely.verifyCredentials();
			System.out.println("Connected to SendSafely as user: " + userEmail);
			try {
				//com.sendsafely.Package p = sendSafely.getPackageInformation("85GX-10PU");
				//
				//File addedFile = p.getFiles().get(0);
	            
	            //java.io.File tfile = sendSafely.downloadFile(p.getPackageId(), addedFile.getFileId(), p.getKeyCode());
	            
	            
	            String link = "https://demo-stage.sendsafely.com/receive/?packageCode=yE32UlIoUoNbXOaTAcomYQUr-J4N7b3iZE8VALhQuwk#keycode=GhwisJDe0uJEdqR1Z0_n-2b_SvVxkr9M0CMH8Kv6wAI";
				String destination = "/Users/michaelnowak";
				
				Package pkgInfoFromLink = sendSafely.getPackageInformationFromLink(link);
				List<File> fileList = pkgInfoFromLink.getFiles();
				for(Iterator<File> i = fileList.iterator(); i.hasNext(); ) {
					  File item = i.next();
					  java.io.File dest = new java.io.File(destination+"/"+item.getFileName());
					  java.io.File newFileToSave = sendSafely.downloadFile(pkgInfoFromLink.getPackageId(), item.getFileId(), pkgInfoFromLink.getKeyCode(), new ProgressCallback());
					  FileUtils.moveFile(newFileToSave, dest);
					  System.out.println("Download Complete");
				}	
	            
	           
			}  catch (DownloadFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PasswordRequiredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PackageInformationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}


}
