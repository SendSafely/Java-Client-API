
package com.sendsafely.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.sendsafely.File;
import com.sendsafely.SendSafely;
import com.sendsafely.exceptions.ApproverRequiredException;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.DownloadFileException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PasswordRequiredException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.UploadFileException;
import com.sendsafely.file.DefaultFileManager;
import com.sendsafely.file.FileManager;

public class ZeroFileUploadTesting {

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
				com.sendsafely.Package p = sendSafely.createPackage();
				sendSafely.addRecipient(p.getPackageId(), "michael+23323@sendsafely.com");
				
				FileManager uploadManager = new DefaultFileManager(new java.io.File("/Users/michaelnowak/d1.sql"));
	            File addedFile = sendSafely.encryptAndUploadFile(p.getPackageId(), p.getKeyCode(), uploadManager);
	            sendSafely.finalizePackage(p.getPackageId(), p.getKeyCode());
	            
	            java.io.File tfile = sendSafely.downloadFile(p.getPackageId(), addedFile.getFileId(), p.getKeyCode());
	            System.out.println(tfile.getName());
			} catch (CreatePackageFailedException | LimitExceededException | UploadFileException | RecipientFailedException | FinalizePackageFailedException | ApproverRequiredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DownloadFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PasswordRequiredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}


}
