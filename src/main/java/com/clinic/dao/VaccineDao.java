package com.clinic.dao;

import com.clinic.entity.VaccineRecord;

public interface VaccineDao {
	
	VaccineRecord getVaccineRecord (int userId, int childId, int batch, String vaccineCode) throws Exception;
	
}
