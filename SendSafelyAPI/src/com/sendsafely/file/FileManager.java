package com.sendsafely.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileManager {

	public long length() throws IOException;
	public String getName() throws IOException;
	public InputStream getInputStream() throws IOException;
    public OutputStream getOutputStream() throws IOException;
    public FileManager createTempFile(String prefix, String suffix, long fileSize) throws IOException;
    public void remove() throws IOException;
	
}
