package com.sendsafely.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.sendsafely.SendSafely;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.RecipientFailedException;

public class RemoveDropzoneRecipientHandler {

	@Test
	public void testRemoveDropzoneRecipient() {
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
			sendSafely.removeDropzoneRecipient(email);
			assertTrue(true);
		} catch (RecipientFailedException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}

}
