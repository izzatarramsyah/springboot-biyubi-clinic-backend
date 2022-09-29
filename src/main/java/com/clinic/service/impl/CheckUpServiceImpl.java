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
import com.clinic.entity.GrowthDtl;
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
	public boolean addCheckUpRecord(CheckUpRequest param) throws Exception {
		boolean result = false;
		CheckUpMaster checkUpMaster = masterDao.getListMstCheckUpByBatch(param.getBatch(), param.getMstCode());
		CheckUpRecord record = new CheckUpRecord();
		record.setUserId( param.getUserId() );
		record.setChildId( param.getChildId() );
		record.setMstCode( checkUpMaster.getCode() );
		record.setCheckUpDate( param.getCheckUpDate() );
		record.setCreatedDtm( new Date() );
		record.setCreatedBy( "SYSTEM" );
		int id = checkUpDao.addCheckUpRecord( record );
		if ( id > 0 ) {
			GrowthDtl growthDtl = new GrowthDtl();
			growthDtl.setMstCode( checkUpMaster.getCode() );
			growthDtl.setRecId( id );
			growthDtl.setWeight( param.getWeight() );
			growthDtl.setLength( param.getLength() );
			growthDtl.setHeadDiameter( param.getHeadDiameter() );
			growthDtl.setNotes( param.getNotes() );
			growthDtl.setCreatedDtm( new Date() );
			growthDtl.setCreatedBy( "SYSTEM" );
			boolean growthSaved = checkUpDao.addGrowthDtl( growthDtl );
			if ( growthSaved ) result = true;
			
		}
		return result;
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
			GrowthDtl growthDtl = new GrowthDtl();

			if (rec != null ) {
				growthDtl = checkUpDao.getGrowthDtl(cm.getCode(), rec.getId());
				if (growthDtl != null) {
					schedule.setWeight(growthDtl.getWeight());
					schedule.setLength(growthDtl.getLength());
					schedule.setHeadDiameter(growthDtl.getHeadDiameter());
					schedule.setNotes(growthDtl.getNotes());
					schedule.setCheckUpDate( Util.formatDate( rec.getCheckUpDate()) );
					if (growthDtl.getMstCode().equals("ACT_000")) {
						schedule.setScheduleDate( Util.formatDate( child.getBirthDate() ) );
						date = child.getBirthDate();
					}
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
	public GrowthDtl getGrowthDtl(String mstCode, int recId) throws ParseException {
		return checkUpDao.getGrowthDtl(mstCode, recId);
	}

	@Override
	public CheckUpRecord getCheckUpRecord(int userId, int childId, String mstCode) throws Exception {
		return checkUpDao.getCheckUpRecord(userId, childId, mstCode);
	}

	@Override
	public boolean updateGrowthDtl(CheckUpRequest checkUpRequest) throws Exception {
		CheckUpRecord rec = checkUpDao.getCheckUpRecord ( checkUpRequest.getUserId(), checkUpRequest.getChildId(), checkUpRequest.getMstCode());
		GrowthDtl dtl = new GrowthDtl();
		dtl.setWeight( checkUpRequest.getWeight() );
		dtl.setLength( checkUpRequest.getLength() );
		dtl.setHeadDiameter( checkUpRequest.getHeadDiameter() );
		dtl.setNotes( checkUpRequest.getNotes() );
		dtl.setRecId( rec.getId() );
		dtl.setLastUpdDtm( new Date() );
		dtl.setLastUpdBy( "SYSTEM" );
		return checkUpDao.updateGrowthDtl( dtl );
	}
	
}
