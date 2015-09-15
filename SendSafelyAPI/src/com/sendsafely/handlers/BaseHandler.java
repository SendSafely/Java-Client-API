package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.BaseRequest;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.progress.DefaultProgress;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.Progress;
import com.sendsafely.utils.SendUtil;

public abstract class BaseHandler 
{
	protected UploadManager uploadManager;
	
	protected BaseHandler(UploadManager uploadManager)
	{
		this.uploadManager = uploadManager;
	}
	
	protected <T> T send(BaseRequest request, T returnObject) throws SendFailedException, IOException
	{
		SendUtil util = new SendUtil(uploadManager);
		return util.send(request.getPath(), returnObject, request);
	}
	
}
