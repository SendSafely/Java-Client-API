package com.sendsafely.utils;

import java.util.TimerTask;

import com.sendsafely.ProgressInterface;
import com.sendsafely.progress.DefaultProgress;

public class Progress extends TimerTask {

	private ProgressInterface callback;
	private long total;
	private long current;
    private String fileId;
	
	public Progress(ProgressInterface callback, String fileId)
	{
		this.callback = callback;
        this.fileId = fileId;
	}
	
	@Override
	public void run() {
		if(callback != null) {
			double progress = (double)Math.min(((double)current)/((double)total), 1);
			callback.updateProgress(fileId, progress);
		}
	}
	
	
	

	public void finished() {
		callback.updateProgress(this.fileId, 1);
	}

	public void setTotal(long total)
	{
		this.total = total;
	}
	
	public void updateCurrent(long current)
	{
		this.current += current;
	}
	
	public void resetCurrent()
	{
		this.current = 0;
	}
	
}
