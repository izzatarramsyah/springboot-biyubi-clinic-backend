package com.clinic.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.config.variable.ApplicationConstant;
import com.clinic.dao.CheckUpDao;
import com.clinic.datamapper.CheckUpRecordMapper;
import com.clinic.datamapper.GrowthDtlMapper;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.GrowthDtl;

@Repository
public class CheckUpDaoImpl implements CheckUpDao {
	
	private static final Logger LOG = LogManager.getLogger(CheckUpDaoImpl.class);

	public static final String GET_CURRENT_CHECK_HEALTH_BY_BATCH_MST_CODE = "SELECT A.*  "
			+ "FROM TBL_CHECK_UP_RECORD A "
			+ "WHERE A.USER_ID = ? "
			+ "AND A.CHILD_ID = ? "
			+ "AND A.MST_CODE = ? ";
	
	public static final String GET_GROWTH_DTL = "SELECT A.* "+
			  " FROM TBL_GROWTH_DTL A "+
			  " WHERE A.MST_CODE = ? "+
			  " AND A.REC_ID = ? ";
	
	public static final String GET_LIST_CHECK_HEALTH = "SELECT A.* "+
			  " FROM TBL_CHECK_UP_RECORD A "+
			  " WHERE A.USER_ID = ? "+
			  " AND A.CHILD_ID = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CheckUpRecord getCheckHealth(int userId, int childId, String mstCode) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CURRENT_CHECK_HEALTH_BY_BATCH_MST_CODE);
		List < CheckUpRecord > result = new ArrayList < CheckUpRecord >();
		try{
			result = jdbcTemplate.query(GET_CURRENT_CHECK_HEALTH_BY_BATCH_MST_CODE, new Object[] { userId, childId, mstCode }, new CheckUpRecordMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}
	
	@Override
	public GrowthDtl getGrowthDtl (String mstCode, int recId) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_GROWTH_DTL);
		List < GrowthDtl > result = new ArrayList < GrowthDtl >();
		try{
			result = jdbcTemplate.query(GET_GROWTH_DTL, new Object[] { mstCode, recId }, new GrowthDtlMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}
	
	@Override
	public List < CheckUpRecord > getListCheckHealth(int userId, int childId) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_CHECK_HEALTH);
		List < CheckUpRecord > result = new ArrayList < CheckUpRecord >();
		try{
			result = jdbcTemplate.query(GET_LIST_CHECK_HEALTH, new Object[] { userId, childId }, new CheckUpRecordMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

}
