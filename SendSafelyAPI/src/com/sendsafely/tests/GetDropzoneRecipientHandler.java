package com.sendsafely.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.sendsafely.Package;
import com.sendsafely.SendSafely;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.RecipientFailedException;

public class GetDropzoneRecipientHandler {

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
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			List<String> result = sendSafely.getDropzoneRecipient();
			System.out.println("result is... " + result);
			assertTrue(result.size()>=0);
		} catch (RecipientFailedException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}

}
