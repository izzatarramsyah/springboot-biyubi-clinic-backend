package com.clinic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.MasterDao;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.VaccineMaster;
import com.clinic.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService{
	
	@Autowired
	MasterDao mstDao;

	@Override
	public List < VaccineMaster > getListMstVaccine() throws Exception {
		return mstDao.getListMstVaccine();
	}

	@Override
	public List <CheckUpMaster> getListMstCheckUp() throws Exception {
		return mstDao.getListMstCheckUp();
	}

	@Override
	public CheckUpMaster getMstCheckUpByCode(String code) throws Exception {
		return mstDao.getMstCheckUpByCode(code);
	}
	
}
