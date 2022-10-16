package com.clinic.service;

import java.text.ParseException;
import java.util.List;

import com.clinic.api.object.CheckUpRequest;
import com.clinic.api.object.CheckUpSchedule;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.UserAdmin;

public interface CheckUpService {
	
	boolean addCheckUpRecord ( UserAdmin user, CheckUpRequest checkHealth ) throws Exception;

	boolean updateCheckUpRecord (UserAdmin user, CheckUpRequest checkHealth) throws Exception;

	List < CheckUpSchedule > getSchedule (int userId, int childId) throws Exception, ParseException;
	
	List < CheckUpRecord > getListCheckUpRecord ( int userId, int childId ) throws Exception;

	CheckUpRecord getCheckUpRecord ( int userId, int childId, String mstCode) throws Exception;

}
