package com.clinic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.api.object.VaccineRequest;
import com.clinic.constant.Constant;
import com.clinic.dao.ApplicationConfigDao;
import com.clinic.dao.MasterDao;
import com.clinic.entity.ApplicationConfig;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.UserAdmin;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMstDtl;
import com.clinic.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService{
	private static final Logger LOG = LogManager.getLogger(MasterServiceImpl.class);

	@Autowired
	MasterDao mstDao;
	
	@Autowired
	ApplicationConfigDao applicationConfigDao;

	@Override
	public List < VaccineMaster > getListMstVaccine() throws Exception {
		List < VaccineMaster > result = mstDao.getListMstVaccine();
		for (VaccineMaster vm : result) {
			List < VaccineMstDtl > detail = new ArrayList < VaccineMstDtl >();
			for (VaccineMstDtl dtl : mstDao.getListVaccineMstDtl(vm.getVaccineCode())) {
				detail.add(dtl);
			}
			vm.setDetail( detail );
		}
		return result;
	}

	@Override
	public VaccineMaster getMstVaccineByCode(String code) throws Exception {
		return mstDao.getMstVaccineByCode(code);
	}
	
	@Override
	public boolean addVaccineMaster(UserAdmin user, VaccineRequest vaccineRq) throws Exception {
		VaccineMaster vm = mstDao.getMstVaccineByName(vaccineRq.getVaccineName());
		if (vm == null) {
			int count = mstDao.countVaccineMaster();
			String code = "VACCINE_" + String.format("%03d", ++count);	
			VaccineMaster mst = new VaccineMaster();
			mst.setVaccineCode( code );
			mst.setVaccineName( vaccineRq.getVaccineName() );
			mst.setVaccineType( vaccineRq.getVaccineType() );
			mst.setExpDays( vaccineRq.getExpDays() );
			mst.setStatus( "ACTIVE" );
			mst.setNotes( vaccineRq.getNotes() );
			mst.setCreatedDtm( new Date() );
			mst.setCreatedBy( user.getUsername() );
			boolean isSaved = mstDao.addVaccineMaster( mst );
			if ( isSaved ) {
				VaccineMstDtl dtl = new VaccineMstDtl();
				dtl.setVaccineCode( code );
				dtl.setBatch( vaccineRq.getBatch() );
				dtl.setCreatedDtm( new Date() );
				dtl.setCreatedBy( user.getUsername() );
				boolean isSavedDtl = mstDao.addVaccineMstDtl( dtl );
				if ( isSavedDtl ) return true;
			} 
		} else {
			VaccineMstDtl dtl = new VaccineMstDtl();
			dtl.setVaccineCode( vm.getVaccineCode() );
			dtl.setBatch( vaccineRq.getBatch() );
			dtl.setCreatedDtm( new Date() );
			dtl.setCreatedBy( user.getUsername() );
			boolean isSavedDtl = mstDao.addVaccineMstDtl( dtl );
			if ( isSavedDtl ) return true;
		}
		return false;
	}

	@Override
	public boolean updateVaccineMaster(UserAdmin user, VaccineRequest rq) throws Exception {
		VaccineMaster mst = new VaccineMaster();
		mst.setVaccineCode( rq.getVaccineCode() );
		mst.setVaccineName( rq.getVaccineName() );
		mst.setVaccineType( rq.getVaccineType() );
		mst.setExpDays( rq.getExpDays() );
		mst.setStatus( "ACTIVE" );
		mst.setNotes( rq.getNotes() );
		mst.setCreatedDtm( new Date() );
		mst.setCreatedBy( user.getUsername() );
		return mstDao.updateVaccineMaster( mst );
	}
	
	@Override
	public boolean changeStatusVaccineMaster(UserAdmin user, VaccineRequest rq) throws Exception {
		VaccineMaster mst = new VaccineMaster();
		mst.setVaccineCode( rq.getVaccineCode() );
		mst.setStatus( rq.getStatus() );
		mst.setLastUpdDtm( new Date() );
		mst.setLastUpdBy( user.getUsername() );
		return mstDao.changeStatusVaccineMaster( mst );
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
	public boolean addCheckUpMaster(UserAdmin user, CheckUpMaster checkUpMaster) throws Exception {
		int count = mstDao.countVaccineCheckUp();
		String code = "ACT_" + String.format("%03d", ++count);
		checkUpMaster.setCode(code);
		checkUpMaster.setStatus("ACTIVE");
		checkUpMaster.setCreatedDtm(new Date());
		checkUpMaster.setCreatedBy( user.getUsername() );
		return mstDao.addCheckUpMaster(checkUpMaster);
	}

	@Override
	public boolean updateCheckUpMaster(UserAdmin user, CheckUpMaster checkUpMaster) throws Exception {
		checkUpMaster.setLastUpdDtm(new Date());
		checkUpMaster.setLastUpdBy( user.getUsername() );
		return mstDao.updateCheckUpMaster(checkUpMaster);
	}

	@Override
	public boolean changeStatusCheckUpMaster(UserAdmin user, CheckUpMaster checkUpMaster) throws Exception {
		checkUpMaster.setLastUpdDtm(new Date());
		checkUpMaster.setLastUpdBy( user.getUsername() );
		return mstDao.changeStatusCheckUpMaster(checkUpMaster);
	}
	
	@Override
	public String category(String type, long month, double value) throws Exception {
		String category = null;
		if (type.equals(Constant.WEIGHT)) category = Constant.VERY_UNDERWEIGHT;
		if (type.equals(Constant.LENGTH)) category = Constant.VERY_UNDERLENGTH;
		if (type.equals(Constant.HEAD_CIRCUMFERENCE)) category = Constant.VERY_MIKROSEFALI;
		List < ApplicationConfig > appConf = applicationConfigDao.get(Constant.APP_NAME, Constant.INSTANCE_NAME);
		for (ApplicationConfig config : appConf) {
			String[] paramName = config.getParamName().split("_");
			String paramType = paramName[0];
			int paramMonth = Integer.parseInt(paramName[1]);
			String[] paramValue = config.getParamValue().split("_");
			double paramVal = Double.parseDouble(paramValue[0]);
			String paramCategory = paramValue[1];
			if (paramType.equals(type)) {
				if (paramMonth == month) {
					if (paramVal <= value) {
						category = paramCategory;
					}
				}
			}
		}
		return category;
	}

	@Override 
	public VaccineMaster getMstVaccineByName(String name) throws Exception {
		return mstDao.getMstVaccineByName(name);
	}

	@Override
	public CheckUpMaster getListMstCheckUpByBatch(int batch) throws Exception {
		return mstDao.getListMstCheckUpByBatch(batch);
	}

}
