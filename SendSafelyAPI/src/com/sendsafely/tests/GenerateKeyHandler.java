package com.sendsafely.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.junit.Test;

import com.sendsafely.SendSafely;
import com.sendsafely.credentials.DefaultCredentials;
import com.sendsafely.exceptions.EnterpriseInfoFailedException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.TwoFactorAuthException;

public class GenerateKeyHandler {

	@Test
	public void testGenerateKeyHandler() throws InvalidCredentialsException {
		Properties configFile = new Properties();
		String host = null;
		String username=null;
		String password=null;
		
		try {
			configFile.load(Test.class.getClassLoader().getResourceAsStream("config.properties"));
			host = configFile.getProperty("host");
			username = configFile.getProperty("username");
			password = configFile.getProperty("password");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		
		SendSafely sendSafely = new SendSafely(host);
		try{
			sendSafely.generateAPIKey(host, username, password, "SendSafely CLI Key (auto generated)");
		}catch(TwoFactorAuthException e){
			Scanner keyboard = new Scanner(System.in);
		    System.out.println("Input validation token: ");
		    String input = keyboard.next();
			try {
				DefaultCredentials defaultCredentials = null;
				defaultCredentials = sendSafely.generateKey2FA(host, e.getValidationToken(), input, "SendSafely CLI Key (auto generated)");
				assertNotNull(defaultCredentials); 
			} catch (EnterpriseInfoFailedException e1) {
				// TODO Auto-generated catch block
				fail(e1.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}

}
