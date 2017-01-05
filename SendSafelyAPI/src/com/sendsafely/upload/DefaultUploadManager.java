package com.sendsafely.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import com.sendsafely.connection.ConnectionManager;
import com.sendsafely.credentials.CredentialsManager;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.CredentialsException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.file.FileManager;
import com.sendsafely.json.JsonManager;
import com.sendsafely.utils.Progress;

public class DefaultUploadManager implements UploadManager {
	
	private final String CONTENT_TYPE = "application/json";
	private final String FILE_UPLOAD_CONTENT_TYPE = "multipart/form-data;";
	private final String CRLF = "\r\n";
	private final int BUFFER_SIZE = 1024;
	
	private CredentialsManager credentialsManager;
	private ConnectionManager conn;
    private JsonManager jsonManager;
	private InputStream content;
	
	public DefaultUploadManager(ConnectionManager connManager, CredentialsManager credentialsManager, JsonManager jsonManager)
	{
		this.credentialsManager = credentialsManager;
		this.conn = connManager;
        this.jsonManager = jsonManager;
	}

	@Override
	public void send(String path, HTTPMethod method, String data) throws SendFailedException, IOException 
	{
		URL url = createUrl(path);
		conn.open(url);
		
		addCredentials(data);
		
		populateHeaders(method.toString(), CONTENT_TYPE);
		
		if(!"".equals(data))
		{
			conn.send(data);
		}
		
		this.content = conn.getResponse();
	}
	
	@Override
	public String sendFile(String path, FileManager file, String filename, String data, Progress progress) throws SendFailedException, IOException
	{
		URL url = createUrl(path);
		
		String boundary = Long.toHexString(System.currentTimeMillis());
		
		conn.open(url);
		
		addCredentials(data);
		
		// Set api key header.
		String contentType = FILE_UPLOAD_CONTENT_TYPE + " boundary=" + boundary;
		populateHeaders("POST", contentType);
		
		OutputStream output = conn.getOutputStream();
		
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true); // true, autoflush
		
		setParam("requestData", data, boundary, writer);
		
		sendBinaryFile(filename, file.getInputStream(), output, writer, boundary, progress);
		
		// End of multipart/form-data.
		writer.append("--" + boundary + "--").append(CRLF);
		writer.flush();
	
		this.content = conn.getResponse();
		String responseVal = getResponse();		
		return responseVal;
	}
	
	@Override
	public String getResponse() throws IOException
	{
		// Wait for response
		BufferedReader in = new BufferedReader(new InputStreamReader(this.content));
		String responseVal = "";
		String line = null;
		while((line = in.readLine()) != null) 
		{
			responseVal += line;
		}
		
		return responseVal;
	}

    @Override
    public JsonManager getJsonManager() {
        return this.jsonManager;
    }
	
	@Override
	public String getContentType() {
		return conn.getHeader("Content-Type");
	}

	@Override
	public InputStream getStream() {
		return this.content;
	}
	
	private URL createUrl(String path) throws SendFailedException
	{
		try {
			return new URL(conn.getHost() + credentialsManager.getAPIPath() + path);
		} catch (MalformedURLException e) {
			throw new SendFailedException(e);
		}
	}
	
	private void sendBinaryFile(String filename, InputStream input, OutputStream output, PrintWriter writer, String boundary, Progress progress) throws IOException {
		
		writer.append("--" + boundary).append(CRLF);
		writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + filename + "\"").append(CRLF);
		writer.append("Content-Type: text/plain; charset=UTF-8").append(CRLF);
		writer.append(CRLF).flush();
		
		try 
		{
			byte[] tmp = new byte[BUFFER_SIZE];
			int l;
			
			while ((l = input.read(tmp, 0, BUFFER_SIZE)) != -1) 
			{
				output.write(tmp, 0, l);
				progress.updateCurrent(l);
				output.flush();
			}
		} 
		finally 
		{
			if(input != null)
				input.close();
		}
		writer.append(CRLF).flush();
	}
	
	private void setParam(String key, String value, String boundary, PrintWriter writer) {
		writer.append("--" + boundary).append(CRLF);
		writer.append("content-disposition: form-data; name=\"" + key + "\"").append(CRLF);
		writer.append("content-type: text/plain; charset=UTF-8").append(CRLF);
		writer.append(CRLF);
		writer.append(value).append(CRLF).flush();
	}
	
	private void addCredentials(String data) throws SendFailedException {
		try {
			credentialsManager.addCredentials(conn, data);
		} catch (CredentialsException e) {
			throw new SendFailedException(e);
		}
	}
	
	private void populateHeaders(String method, String contentType) throws SendFailedException
	{
		conn.addHeader("Content-Type", contentType);
		conn.setRequestMethod(method);
	}

}
