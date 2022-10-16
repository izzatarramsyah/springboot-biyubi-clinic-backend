package com.clinic.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.api.object.CheckUpRequest;
import com.clinic.api.object.CheckUpSchedule;
import com.clinic.dao.CheckUpDao;
import com.clinic.dao.MasterDao;
import com.clinic.dao.UserDao;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.Child;
import com.clinic.entity.UserAdmin;
import com.clinic.service.CheckUpService;
import com.clinic.util.Util;

@Service
public class CheckUpServiceImpl implements CheckUpService{
 
	@Autowired
	CheckUpDao checkUpDao;
	
	@Autowired
	MasterDao masterDao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public boolean addCheckUpRecord(UserAdmin user, CheckUpRequest param) throws Exception {
		CheckUpRecord record = new CheckUpRecord();
		record.setUserId( param.getUserId() );
		record.setChildId( param.getChildId() );
		record.setMstCode( param.getMstCode() );
		record.setCheckUpDate( param.getCheckUpDate() );
		record.setWeight( param.getWeight() );
		record.setLength( param.getLength() );
		record.setHeadDiameter( param.getHeadDiameter() );
		record.setNotes( param.getNotes() );
		record.setCreatedDtm( new Date() );
		record.setCreatedBy( user.getUsername() );
		return checkUpDao.addCheckUpRecord( record );
	}
	
	@Override
	public List < CheckUpSchedule > getSchedule(int userId, int childId) throws Exception, ParseException {
		List < CheckUpSchedule > listCheckUpSchedule = new ArrayList < CheckUpSchedule >();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar schDate = Calendar.getInstance();
		Date date = new Date();
		Child child = userDao.getChildByID( childId );

		for (CheckUpMaster cm : masterDao.getListMstCheckUp()) {
			CheckUpSchedule schedule = new CheckUpSchedule();
			
			schedule.setActName(cm.getActName());
			schedule.setBatch(cm.getBatch());
			schedule.setUserId(userId);
			schedule.setChildId(childId);
			schedule.setCode(cm.getCode());
			schedule.setDescription(cm.getDescription());
			schedule.setNextCheckUpDays(cm.getNextCheckUpDays());

			CheckUpRecord rec = checkUpDao.getCheckUpRecord ( userId, childId, cm.getCode());

			if (rec != null ) {
				schedule.setWeight(rec.getWeight());
				schedule.setLength(rec.getLength());
				schedule.setHeadDiameter(rec.getHeadDiameter());
				schedule.setNotes(rec.getNotes());
				schedule.setCheckUpDate( Util.formatDate( rec.getCheckUpDate()) );
				if (rec.getMstCode().equals("ACT_000")) {
					schedule.setScheduleDate( Util.formatDate( child.getBirthDate() ) );
					date = child.getBirthDate();
				}
			}
			
			if (!cm.getCode().equals("ACT_000")) {
				schDate.setTime(date);
				schDate.add(Calendar.DATE, cm.getNextCheckUpDays());
				schedule.setScheduleDate( Util.formatDate( sdf.parse(sdf.format(schDate.getTime())) ) );
				date = sdf.parse(sdf.format(schDate.getTime()));
			}
			
			listCheckUpSchedule.add(schedule);
		}
		return listCheckUpSchedule;
	}

	@Override
	public List<CheckUpRecord> getListCheckUpRecord(int userId, int childId) throws Exception {
		return checkUpDao.getListCheckUpRecord(userId, childId);
	}

	@Override
	public CheckUpRecord getCheckUpRecord(int userId, int childId, String mstCode) throws Exception {
		return checkUpDao.getCheckUpRecord(userId, childId, mstCode);
	}

	@Override
	public boolean updateCheckUpRecord(UserAdmin user, CheckUpRequest checkHealth) throws Exception {
		CheckUpRecord record = new CheckUpRecord();
		record.setUserId( checkHealth.getUserId() );
		record.setChildId( checkHealth.getChildId() );
		record.setMstCode( checkHealth.getMstCode() );
		record.setCheckUpDate( checkHealth.getCheckUpDate() );
		record.setWeight( checkHealth.getWeight() );
		record.setLength( checkHealth.getLength() );
		record.setHeadDiameter( checkHealth.getHeadDiameter() );
		record.setNotes( checkHealth.getNotes() );
		record.setUpdatedBy(user.getUsername());
		record.setUpdatedDtm( new Date() );
		return false;
	}

}
