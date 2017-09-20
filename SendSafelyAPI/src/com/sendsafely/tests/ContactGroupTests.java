package com.sendsafely.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.sendsafely.ContactGroup;
import com.sendsafely.Package;
import com.sendsafely.SendSafely;
import com.sendsafely.dto.ContactGroupMember;
import com.sendsafely.exceptions.ContactGroupException;
import com.sendsafely.exceptions.AddEmailContactGroupFailedException;
import com.sendsafely.exceptions.CreateContactGroupFailedException;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.GetContactGroupsFailedException;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.RemoveContactGroupAsRecipientFailedException;
import com.sendsafely.exceptions.RemoveContactGroupFailedException;
import com.sendsafely.exceptions.RemoveEmailContactGroupFailedException;

public class ContactGroupTests {

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
			Package pkg = sendSafely.createPackage();
			
			String contactGroupId = sendSafely.createContactGroup("TestGroupName");
			String userId1 = sendSafely.addUserToContactGroup(contactGroupId, "michael+test@sendsafely.net");
			sendSafely.addContactGroupToPackage(pkg.getPackageId(), contactGroupId);
			pkg = sendSafely.getPackageInformation(pkg.getPackageId());
			List<ContactGroup> CG = pkg.getContactGroups();
			ContactGroup c = CG.get(0);
			assertEquals(c.getContactGroupName(), "TestGroupName");
			List<ContactGroupMember> cgmembers = c.getContactGroupMembers();
			ContactGroupMember m = cgmembers.get(0);
			assertEquals(m.getUserEmail(), "michael+test@sendsafely.net");
			sendSafely.removeUserFromContactGroup(contactGroupId, userId1);
			
			
			String userId2 = sendSafely.addUserToContactGroup(contactGroupId, "michael+2test@sendsafely.net");
			pkg = sendSafely.getPackageInformation(pkg.getPackageId());
			CG = pkg.getContactGroups();
			c = CG.get(0);
			assertEquals(c.getContactGroupName(), "TestGroupName");
			cgmembers = c.getContactGroupMembers();
			m = cgmembers.get(0);
			assertEquals(m.getUserEmail(), "michael+2test@sendsafely.net");
			assertNotNull(pkg.getContactGroups());
			
			
			pkg = sendSafely.getPackageInformation(pkg.getPackageId());
			assertNotNull(pkg.getContactGroups());
			
			sendSafely.removeContactGroupFromPackage(pkg.getPackageId(), contactGroupId);
			assertNotNull(sendSafely.getContactGroups());
			sendSafely.deleteContactGroup(contactGroupId);
			assertTrue(true);
		} catch (CreateContactGroupFailedException | AddEmailContactGroupFailedException | RemoveEmailContactGroupFailedException | CreatePackageFailedException | LimitExceededException | ContactGroupException | RemoveContactGroupAsRecipientFailedException | RemoveContactGroupFailedException | GetContactGroupsFailedException | PackageInformationFailedException e) {			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
