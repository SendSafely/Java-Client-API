package com.sendsafely.credentials;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.exceptions.CredentialsException;
import com.sendsafely.exceptions.SignatureCreationFailedException;
import com.sendsafely.utils.CryptoUtil;

public class DefaultCredentials implements CredentialsManager 
{
	private final String apiPath = "/api/v1.1";
	
	private final String API_KEY_HEADER = "ss-api-key";
	private final String TIMESTAMP_HEADER = "ss-request-timestamp";
	private final String SIGNATURE_HEADER = "ss-request-signature";
	
	private String apiKey;
	private String apiSecret;
	
	public DefaultCredentials(String apiKey, String apiSecret)
	{
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	@Override
	public String getAPIPath()
	{
		return apiPath;
	}
	
	@Override
	public void addCredentials(ConnectionManager conn, String data) throws CredentialsException 
	{	
		String dateString = getDateString();
		String signature = createSignature(conn.getURL(), dateString, data);
		
		conn.addHeader(API_KEY_HEADER, apiKey);
		conn.addHeader(TIMESTAMP_HEADER, dateString);
		conn.addHeader(SIGNATURE_HEADER, signature);
	}
	
	private String createSignature(URL url, String dateString, String data) throws CredentialsException 
	{
		String contentToHash = this.apiKey + url.getPath() + dateString + data;
		String signature;
		try {
			signature = CryptoUtil.Sign(this.apiSecret, contentToHash);
		} catch (SignatureCreationFailedException e) {
			throw new CredentialsException(e);
		}
		
		return signature;
	}
	
	private String getDateString()
	{
		Date creationTs = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = format.format(creationTs);
		return formattedDate;
	}
}
