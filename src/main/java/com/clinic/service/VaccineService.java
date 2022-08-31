package com.clinic.service;

import java.util.List;

import com.clinic.api.object.VaccineSchedule;

public interface VaccineService {

	List < VaccineSchedule > getSchedule (int userId, int childId) throws Exception;
	
}
