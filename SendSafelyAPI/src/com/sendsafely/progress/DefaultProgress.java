package com.sendsafely.progress;

import com.sendsafely.ProgressInterface;

public class DefaultProgress implements ProgressInterface {

	@Override
	public void updateProgress(String fileId, double progress) {
		// Do nothing
	}

	@Override
	public void gotFileId(String fileId) {
		// Do nothing
	}

}
