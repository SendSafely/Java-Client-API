package com.sendsafely.tests;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.sendsafely.ProgressInterface;

public class ProgressCallback implements ProgressInterface {

	@Override
	public void updateProgress(String fileId, double progress) {
		System.out.print("\r"+ MessageFormat.format("{0,number,#.##%}      ", progress));
		
	}

	@Override
	public void gotFileId(String fileId) {
		System.out.println("Got File Id: " + fileId);	
	}
	
	
}
