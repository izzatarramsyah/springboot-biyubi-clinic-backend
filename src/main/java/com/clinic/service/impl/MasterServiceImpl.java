package com.clinic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.constant.Constant;
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
		if (type.equals(Constant.WEIGHT)) category = Constant.VERY_UNDERWEIGHT;
		if (type.equals(Constant.LENGTH)) category = Constant.VERY_UNDERLENGTH;
		if (type.equals(Constant.HEAD_CIRCUMFERENCE)) category = Constant.VERY_MIKROSEFALI;
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
