package com.sendsafely.utils;

import java.util.TimerTask;

import com.sendsafely.ProgressInterface;
import com.sendsafely.progress.DefaultProgress;

public class Progress extends TimerTask {

	private ProgressInterface callback;
	private long total;
	private long current;
	
	public Progress(ProgressInterface callback)
	{
		this.callback = callback;
	}
	
	@Override
	public void run() {
		if(callback != null) {
			double progress = Math.min(((double)current)/((double)total), 1);
			callback.updateProgress(progress);
		}
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
