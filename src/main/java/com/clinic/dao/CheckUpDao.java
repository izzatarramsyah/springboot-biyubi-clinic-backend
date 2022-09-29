package com.clinic.dao;

import java.util.List;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.GrowthDtl;

public interface CheckUpDao {
			
	CheckUpRecord getCheckUpRecord ( int userId, int childId, String mstCode) throws Exception;
	
	GrowthDtl getGrowthDtl (String mstCode, int recId);

	List < CheckUpRecord > getListCheckUpRecord ( int userId, int childId ) throws Exception;
	
	int addCheckUpRecord ( CheckUpRecord checkHealth ) throws Exception;

	boolean addGrowthDtl (GrowthDtl growthDtl);

	boolean updateGrowthDtl (GrowthDtl growthDtl);

}
