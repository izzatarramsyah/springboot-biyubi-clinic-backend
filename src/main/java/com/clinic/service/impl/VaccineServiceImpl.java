package com.clinic.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.api.object.VaccineSchedule;
import com.clinic.dao.MasterDao;
import com.clinic.dao.VaccineDao;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMstDtl;
import com.clinic.entity.VaccineRecord;
import com.clinic.service.VaccineService;
import com.clinic.util.Util;

@Service
public class VaccineServiceImpl implements VaccineService{
	
	@Autowired
	VaccineDao vaccineDao;
	
	@Autowired
	MasterDao masterDao;
	
	@Override
	public List < VaccineSchedule > getSchedule(int userId, int childId) throws Exception {
		List < VaccineSchedule >  listSchedule = new ArrayList < VaccineSchedule >();
		for (VaccineMaster vm : masterDao.getListMstVaccine()) {
			for (VaccineMstDtl vmDtl : masterDao.getListVaccineMstDtl(vm.getVaccineCode())) {
				VaccineSchedule schedule = new VaccineSchedule();
				schedule.setUserId(userId);
				schedule.setChildId(childId);
				schedule.setVaccineName(vm.getVaccineName());
				schedule.setVaccineType(vm.getVaccineType());
				schedule.setVaccineCode(vm.getVaccineCode());
				schedule.setExpDays(vm.getExpDays());
				schedule.setBatch(vmDtl.getBatch());
				VaccineRecord vaccineRecord = vaccineDao.getVaccineRecord(userId, childId, vmDtl.getBatch(), vmDtl.getVaccineCode());
				if (vaccineRecord != null) {
					schedule.setVaccineDate(Util.formatDate(vaccineRecord.getVaccineDate()));
					schedule.setNotes(vaccineRecord.getNotes());
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					Calendar expDate = Calendar.getInstance();
					expDate.setTime(vaccineRecord.getVaccineDate());
					expDate.add(Calendar.DATE, vm.getExpDays());
					schedule.setExpDate( Util.formatDate( sdf.parse(sdf.format(expDate.getTime())) ) );
				}
				listSchedule.add(schedule);
			}
		}
		return listSchedule;
	}

}
