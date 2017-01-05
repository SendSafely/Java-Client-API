package com.sendsafely.file;

import java.io.*;

public class DefaultFileManager implements FileManager {
	
	private File file;
	
	public DefaultFileManager(File file) throws IOException
	{
		if(! file.exists()) { 
			throw new IOException("File not found: " + file.getName());
		}
		if(file.isDirectory()) { 
			throw new IOException("Can't upload directory: " + file.getName());
		}
		this.file = file;
	}

	@Override
	public long length() {
		return file.length();
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		try {
			return new FileInputStream(this.file);
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		}
	}

    @Override
    public OutputStream getOutputStream() throws IOException {
        try {
            return new FileOutputStream(this.file);
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }

    @Override
    public FileManager createTempFile(String prefix, String suffix, long fileSize) throws IOException {
        return new DefaultFileManager(File.createTempFile(prefix, suffix));
    }

    @Override
    public void remove() throws IOException {
        this.file.delete();
    }

}
