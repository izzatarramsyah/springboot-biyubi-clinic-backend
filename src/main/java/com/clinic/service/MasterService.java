package com.clinic.service;

import java.util.List;

import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.VaccineMaster;

public interface MasterService {

	List < VaccineMaster > getListMstVaccine () throws Exception;
	
	List < CheckUpMaster > getListMstCheckUp () throws Exception;

	CheckUpMaster getMstCheckUpByCode ( String code ) throws Exception;

	String category ( String type, long month, double value ) throws Exception; 

}
