package com.clinic.dao;

import java.util.List;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.GrowthDtl;

public interface CheckUpDao {
			
	CheckUpRecord getCheckHealth ( int userId, int childId, String mstCode) throws Exception;
	
	GrowthDtl getGrowthDtl (String mstCode, int recId);

	List < CheckUpRecord > getListCheckHealth ( int userId, int childId ) throws Exception;

}
