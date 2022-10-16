package com.clinic.dao;

import java.util.List;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.GrowthDtl;

public interface CheckUpDao {
			
	CheckUpRecord getCheckUpRecord ( int userId, int childId, String mstCode) throws Exception;
	
	List < CheckUpRecord > getListCheckUpRecord ( int userId, int childId ) throws Exception;
	
	boolean addCheckUpRecord ( CheckUpRecord checkHealth ) throws Exception;

	boolean updateCheckUpRecord (CheckUpRecord checkHealth) throws Exception;

}
