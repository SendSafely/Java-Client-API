package com.sendsafely.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.sendsafely.exceptions.SendFailedException;

/**
 * @description Interface for creating custom connection managers. By default a HTTPURLConnection is used to upload files. Implement this interface with your own HTTP Handling to use a different interface than the default.
 */
public interface ConnectionManager {

	/**
	 * @description Open a new connection to a given URL.
	 * @param url
	 */
	public void open(URL url) throws IOException;
	
	/**
	 * @description Get the URL we are currently connected to.
	 */
	public URL getURL();
	
	/**
	 * @description Return the host of the of the current connection.
	 */
	public String getHost();
	
	/**
	 * @description Add an HTTP Header with a value to the request object.
	 */
	public void addHeader(String header, String value);
	
	/**
	 * @description Set the request method of the request. This should always be one of GET, POST, DELETE, PUT or OPTIONS
	 */
	public void setRequestMethod(String method) throws SendFailedException;
	
	/**
	 * @description Upload data to the server. Before this function is called, a connection will always have been opened by calling the open function.
	 */
	public void send(String data) throws IOException;
	
	/**
	 * @description Return an output stream. The output stream will be used to upload larger data objects such as files.
	 */
	public OutputStream getOutputStream() throws IOException;
	
	/**
	 * @description Get the server response. Will be called after first calling send or getOutputStream
	 */
	public InputStream getResponse() throws IOException;
	
	/**
	 * @description Get a header from the response.
	 */
	public String getHeader(String header);
	
	/**
	 * @description Gets all headers from the response
	 */
	public Map<String, List<String>> getHeaders();
	
	/**
	 * @throws IOException 
	 * @description gets response code
	 */
	public int getResponseCode() throws IOException;
	
	/**
	 * @throws IOException 
	 * @description gets response message
	 */
	public String getResponseMessage() throws IOException;
	
	/**
	 * @description returns the content type
	 */
	public String getContentType();
	
	/**
	 * @description Get the server error stream.
	 */
	public InputStream getErrorStream() throws IOException;
	
}
