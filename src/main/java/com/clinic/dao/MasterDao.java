package com.clinic.dao;

import java.util.List;

import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.MstWLHStandard;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMstDtl;

public interface MasterDao {

	List < VaccineMaster > getListMstVaccine () throws Exception;
	
	List < VaccineMstDtl > getListVaccineMstDtl (String vaccineCode) throws Exception;

	List < CheckUpMaster > getListMstCheckUp () throws Exception;
	
	CheckUpMaster getMstCheckUpByCode ( String code ) throws Exception;

	List < MstWLHStandard > getListMstWLHStandard ( String type )throws Exception;

}
