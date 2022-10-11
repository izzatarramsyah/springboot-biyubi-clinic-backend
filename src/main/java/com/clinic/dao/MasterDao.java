package com.clinic.dao;

import java.util.List;

import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMstDtl;

public interface MasterDao {

	List < VaccineMaster > getListMstVaccine () throws Exception;
	
	VaccineMaster getMstVaccineByCode ( String code ) throws Exception;
	
	List < VaccineMstDtl > getListVaccineMstDtl (String vaccineCode) throws Exception;

	List < CheckUpMaster > getListMstCheckUp () throws Exception;
	
	CheckUpMaster getMstCheckUpByCode ( String code ) throws Exception;

	CheckUpMaster getListMstCheckUpByBatch (int batch ) throws Exception;

	VaccineMaster getMstVaccineByName ( String name ) throws Exception;
	
	int countVaccineMaster () throws Exception;
	
	boolean addVaccineMaster ( VaccineMaster vaccineMaster ) throws Exception;

	boolean addVaccineMstDtl (VaccineMstDtl vaccineMstDtl) throws Exception;

	boolean updateVaccineMaster ( VaccineMaster vaccineMaster ) throws Exception;
	
	boolean changeStatusVaccineMaster ( VaccineMaster vaccineMaster ) throws Exception;
	
	int countVaccineCheckUp () throws Exception;

	boolean addCheckUpMaster ( CheckUpMaster checkUpMaster ) throws Exception;
	
	boolean updateCheckUpMaster ( CheckUpMaster checkUpMaster ) throws Exception;
	
	boolean changeStatusCheckUpMaster ( CheckUpMaster checkUpMaster ) throws Exception;

}
