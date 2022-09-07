package com.clinic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.MasterDao;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.MstWLHStandard;
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
	
	@Override
	public String category(String type, long month, double value) throws Exception {
		String category = null;
		if (type.equals("WEIGHT")) {
			category = "VERY UNDERWEIGHT";
		} else if (type.equals("LENGTH")) {
			category = "VERY UNDERLENGTH";
		} else if (type.equals("HEAD CIRCUMFERENCE")) {
			category = "VERY MIKROSEFALI";
		}
		for (MstWLHStandard list : mstDao.getListMstWLHStandard(type)) {
			if (list.getMonth() == month) {
				if (list.getValue() <= value) {
					category = list.getCategory();
				}
			}
		}
		return category;
	}
	
}
