package com.sendsafely.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.sendsafely.exceptions.SendFailedException;

public class DefaultConnectionManager implements ConnectionManager {
	
	private HttpURLConnection conn;
	private String host;
	
	public DefaultConnectionManager(String host) {
		this.host = host;
	}
	
	@Override
	public String getHost() {
		return this.host;
	}
	
	@Override
	public void open(URL url) throws IOException {
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
	}
	
	@Override
	public URL getURL()
	{
		return conn.getURL();
	}
	
	@Override
	public void addHeader(String header, String value)
	{
		conn.setRequestProperty(header, value);
	}
	
	@Override
	public void setRequestMethod(String method) throws SendFailedException
	{
		try {
			conn.setRequestMethod(method);
		} catch (ProtocolException e) {
			throw new SendFailedException(e);
		}
	}
	
	@Override
	public void send(String data) throws IOException 
	{
		OutputStream output = conn.getOutputStream();
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
		writer.write(data);
		writer.flush();
		writer.close();
	}
	
	@Override
	public InputStream getResponse() throws IOException
	{
		return conn.getInputStream();
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException
	{
		return conn.getOutputStream();
	}
	
	@Override
	public String getHeader(String header)
	{
		return conn.getHeaderField(header);
	}
	
	@Override
	public Map<String,List<String>> getHeaders(){
		return conn.getHeaderFields();
	}
	
	public int getResponseCode() throws IOException{
		return conn.getResponseCode();
	}
	
	public String getResponseMessage() throws IOException{
		return conn.getResponseMessage();
	}
	
	public String getContentType(){
		return conn.getContentType();
	}
	
	@Override
	public InputStream getErrorStream() throws IOException
	{
		return conn.getErrorStream();
	}

}
