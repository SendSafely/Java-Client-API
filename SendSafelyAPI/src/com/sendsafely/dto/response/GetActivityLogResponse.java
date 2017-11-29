package com.sendsafely.dto.response;

import java.util.Collection;

import com.sendsafely.dto.ActivityLogEntry;

public class GetActivityLogResponse extends BaseResponse {

	private Collection<ActivityLogEntry> activityLogEntries;
	private Long dataCount;


	public Collection<ActivityLogEntry> getActivityLogEntries() {
		return activityLogEntries;
	}

	public void setActivityLogEntries(Collection<ActivityLogEntry> activityLogCollection) {
		this.activityLogEntries = activityLogCollection;
	}

	public void setDataCount(Long count) {
		this.dataCount = count;
	}
	
	public Long getDataCount(){
		return this.dataCount;
	}
}
