package com.clinic.dao;

import com.clinic.entity.VaccineRecord;

public interface VaccineDao {
	
	boolean addVaccineRecord ( VaccineRecord vaccineRecord ) throws Exception;
	
	boolean updateVaccineRecord ( VaccineRecord vaccineRecord ) throws Exception;  

	VaccineRecord getVaccineRecord (int userId, int childId, int batch, String vaccineCode) throws Exception;
	
}
