package com.sendsafely.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;

import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.operator.PGPKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.bc.BcPBEKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.util.encoders.Base64;

import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class MessageUtility 
{
	private final String UPLOAD_TYPE = "JAVA_API";
	
	private UploadManager uploadManager;
	
	public MessageUtility(UploadManager uploadManager)
	{
		this.uploadManager = uploadManager;
	}
	
	public void encryptAndUploadMessage(String message, String encryptionKey, AddMessageRequest request) throws SendFailedException, IOException, MessageException
	{
		String encryptedMessage = encrypt(message, encryptionKey);
		BaseResponse response = upload(encryptedMessage, request);
	
		if(response.getResponse() != APIResponse.SUCCESS) {
			throw new MessageException();
		}
	}
	
	protected AddMessageRequest populateRequest(AddMessageRequest request, String encryptedMessage)
	{
		request.setUploadType(UPLOAD_TYPE);
		request.setMessage(encryptedMessage);
		return request;
	}
	
	protected BaseResponse upload(String encryptedMessage, AddMessageRequest request) throws SendFailedException, IOException
	{
		BaseResponse returnObject = new BaseResponse();
		request = populateRequest(request, encryptedMessage);
		SendUtil util = new SendUtil(uploadManager);
		return util.send(request.getPath(), returnObject, request);
	}
	
	protected String encrypt(String message, String encryptionKey) throws MessageException
	{
		byte[] messageBytes = message.getBytes(Charset.forName("UTF-8"));
		
		String encrypted = "";
        
        try
        {
        	BcPGPDataEncryptorBuilder pdeb = new BcPGPDataEncryptorBuilder(PGPEncryptedDataGenerator.AES_256);
        	pdeb.setWithIntegrityPacket(true);
	        PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(pdeb);
        	
	        ByteArrayOutputStream encOut = new ByteArrayOutputStream();
	        OutputStream out = encOut;
	        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
	
	        OutputStream pOut = lData.open(bOut, // the compressed output stream
	                PGPLiteralData.BINARY, PGPLiteralData.CONSOLE,
	                messageBytes.length, // length of clear data
	                new Date() // current time
	                );
	        pOut.write(messageBytes);
	
	        lData.close();
	        bOut.close();
	
	        PGPKeyEncryptionMethodGenerator method = new BcPBEKeyEncryptionMethodGenerator(encryptionKey.toCharArray());
	        cPk.addMethod(method);
	        
	        byte[] bytes = bOut.toByteArray();
	
	        OutputStream cOut = cPk.open(out, bytes.length);
	
	        cOut.write(bytes); // obtain the actual bytes from the compressed stream
	
	        cOut.close();
	        
	        bOut.close();
	        
	        out.close();
        
	        byte[] encodedValue = Base64.encode(encOut.toByteArray());
	        encrypted = new String(encodedValue);
        }
        catch(Exception e)
        {
        	throw new MessageException(e);
        }
        
        return encrypted;
	}
}
