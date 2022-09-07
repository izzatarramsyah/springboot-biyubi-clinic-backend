package com.clinic.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.dao.CheckUpDao;
import com.clinic.dao.MasterDao;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.GrowthDtl;
import com.clinic.service.CheckUpService;
import com.clinic.util.Util;

@Service
public class CheckUpServiceImpl implements CheckUpService{

	@Autowired
	CheckUpDao checkHealthDao;
	
	@Autowired
	MasterDao masterDao;
	
	@Override
	public List < CheckUpSchedule > getSchedule(int userId, int childId) throws Exception, ParseException {
		List < CheckUpSchedule > listCheckUpSchedule = new ArrayList < CheckUpSchedule >();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar schDate = Calendar.getInstance();
		Date checkUpDate = new Date();
		
		for (CheckUpMaster cm : masterDao.getListMstCheckUp()) {
			CheckUpSchedule schedule = new CheckUpSchedule();
			
			schedule.setActName(cm.getActName());
			schedule.setBatch(cm.getBatch());
			schedule.setUserId(userId);
			schedule.setChildId(childId);
			schedule.setCode(cm.getCode());
			schedule.setDescription(cm.getDescription());
			schedule.setNextCheckUpDays(cm.getNextCheckUpDays());

			CheckUpRecord rec = checkHealthDao.getCheckHealth ( userId, childId, cm.getCode());
			GrowthDtl growthDtl = new GrowthDtl();

			if (rec != null ) {
				growthDtl = checkHealthDao.getGrowthDtl(cm.getCode(), rec.getId());
				if (growthDtl != null) {
					SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
					schedule.setWeight(growthDtl.getWeight());
					schedule.setLength(growthDtl.getLength());
					schedule.setHeadDiameter(growthDtl.getHeadDiameter());
					schedule.setNotes(growthDtl.getNotes());
					String checkUpDt = sd.format(new Date());
					Date newCheckUpt = Util.formatDate(checkUpDt);
					schedule.setCheckUpDate( Util.formatDate(newCheckUpt) );
					if (growthDtl.getMstCode().equals("ACT_000")) {
						checkUpDate = new Date();
						schedule.setScheduleDate( Util.formatDate( checkUpDate ) );
					}
				}
			}
			
			
			if (!cm.getCode().equals("ACT_000")) {
				schDate.setTime(checkUpDate);
				schDate.add(Calendar.DATE, cm.getNextCheckUpDays());
				schedule.setScheduleDate( Util.formatDate( sdf.parse(sdf.format(schDate.getTime())) ) );
				checkUpDate = sdf.parse(sdf.format(schDate.getTime()));
			}
			
			listCheckUpSchedule.add(schedule);
		}
		return listCheckUpSchedule;
	}

	@Override
	public List<CheckUpRecord> getListCheckHealth(int userId, int childId) throws Exception {
		return checkHealthDao.getListCheckHealth(userId, childId);
	}

	@Override
	public GrowthDtl getGrowthDtl(String mstCode, int recId) throws ParseException {
		return checkHealthDao.getGrowthDtl(mstCode, recId);
	}

	@Override
	public CheckUpRecord getCheckHealth(int userId, int childId, String mstCode) throws Exception {
		return checkHealthDao.getCheckHealth(userId, childId, mstCode);
	}
	
}
