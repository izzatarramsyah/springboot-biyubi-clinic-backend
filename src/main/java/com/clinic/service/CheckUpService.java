package com.clinic.service;

import java.text.ParseException;
import java.util.List;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.GrowthDtl;

public interface CheckUpService {
	
	List < CheckUpSchedule > getSchedule (int userId, int childId) throws Exception, ParseException;
	
	List < CheckUpRecord > getListCheckHealth ( int userId, int childId ) throws Exception;

	GrowthDtl getGrowthDtl (String mstCode, int recId) throws ParseException;

}
