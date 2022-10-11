package com.clinic.service;

import java.util.List;

import com.clinic.api.object.VaccineRequest;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.VaccineMaster;

public interface MasterService {

	List < VaccineMaster > getListMstVaccine () throws Exception;
	
	VaccineMaster getMstVaccineByCode ( String code ) throws Exception;
	
	VaccineMaster getMstVaccineByName ( String name ) throws Exception;

	boolean addVaccineMaster ( VaccineRequest vaccineRq ) throws Exception;

	boolean updateVaccineMaster ( VaccineRequest vaccineRq ) throws Exception;
	
	boolean changeStatusVaccineMaster ( VaccineRequest vaccineRq ) throws Exception;

	List < CheckUpMaster > getListMstCheckUp () throws Exception;

	CheckUpMaster getMstCheckUpByCode ( String code ) throws Exception;

	CheckUpMaster getListMstCheckUpByBatch (int batch) throws Exception;

	boolean addCheckUpMaster ( CheckUpMaster checkUpMaster ) throws Exception;
	
	boolean updateCheckUpMaster ( CheckUpMaster checkUpMaster ) throws Exception;
	
	boolean changeStatusCheckUpMaster ( CheckUpMaster checkUpMaster ) throws Exception;

	String category ( String type, long month, double value ) throws Exception; 

}
