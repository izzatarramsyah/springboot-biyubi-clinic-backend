package com.clinic.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clinic.config.variable.ApplicationConstant;
import com.clinic.dao.MasterDao;
import com.clinic.datamapper.CheckUpMasterMapper;
import com.clinic.datamapper.VaccineMasterMapper;
import com.clinic.datamapper.VaccineMstDtlMapper;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMstDtl;

@Repository
public class MasterDaoImpl implements MasterDao{
	
	private static final Logger LOG = LogManager.getLogger(MasterDaoImpl.class);

	public static final String GET_LIST_MST_VACCINE = "SELECT A.* FROM TBL_VACCINE_MASTER A WHERE A.STATUS = 'ACTIVE' ORDER BY A.VACCINE_CODE ASC";

	public static final String GET_LIST_MST_CHECK_UP_ = "SELECT A.* FROM TBL_CHECK_UP_MASTER A WHERE A.STATUS = 'ACTIVE' ORDER BY A.BATCH ASC ";

	public static final String GET_LIST_MST_DTL_VACCINE = "SELECT A.* FROM TBL_VACCINE_MASTER_DTL A "
			+ "WHERE A.VACCINE_CODE = ?"
			+ "ORDER BY A.BATCH ASC ";
	
	public static final String GET_CHECK_UP_MASTER = "SELECT A.*  "
			+ " FROM TBL_CHECK_UP_MASTER A "
			+ " WHERE A.CODE = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List < VaccineMaster > getListMstVaccine () throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_VACCINE);
		List < VaccineMaster > result = new ArrayList <VaccineMaster>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_VACCINE, new Object[] {}, new VaccineMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	@Override
	public List < VaccineMstDtl > getListVaccineMstDtl(String vaccineCode) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_DTL_VACCINE);
		List < VaccineMstDtl > result = new ArrayList <VaccineMstDtl>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_DTL_VACCINE, new Object[] { vaccineCode }, new VaccineMstDtlMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}
	
	@Override
	public List<CheckUpMaster> getListMstCheckUp() throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_CHECK_UP_);
		List < CheckUpMaster > result = new ArrayList <CheckUpMaster>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_CHECK_UP_, new Object[] { }, new CheckUpMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}
	
	@Override
	public CheckUpMaster getMstCheckUpByCode(String code) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CHECK_UP_MASTER);
		List < CheckUpMaster > result = new ArrayList <CheckUpMaster>();
		try{
			result = jdbcTemplate.query(GET_CHECK_UP_MASTER, new Object[] { code }, new CheckUpMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() == 0 ? null : result.get(0);

	}

}
