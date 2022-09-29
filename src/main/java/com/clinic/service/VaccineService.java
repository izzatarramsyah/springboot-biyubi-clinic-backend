package com.clinic.service;

import java.util.List;

import com.clinic.api.object.VaccineSchedule;
import com.clinic.entity.VaccineRecord;

public interface VaccineService {

	boolean addVaccineRecord ( VaccineRecord vaccineRecord ) throws Exception;

	boolean updateVaccineRecord ( VaccineRecord vaccineRecord ) throws Exception;  

	List < VaccineSchedule > getSchedule (int userId, int childId) throws Exception;
	
	VaccineRecord getVaccineRecord (int userId, int childId, int batch, String vaccineCode) throws Exception;

}
