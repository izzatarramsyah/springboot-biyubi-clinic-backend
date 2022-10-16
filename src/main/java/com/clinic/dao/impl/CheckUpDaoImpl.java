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
import com.clinic.dao.CheckUpDao;
import com.clinic.datamapper.CheckUpRecordMapper;
import com.clinic.entity.CheckUpRecord;

@Repository
public class CheckUpDaoImpl implements CheckUpDao {
	
	private static final Logger LOG = LogManager.getLogger(CheckUpDaoImpl.class);

	public static final String GET_CHECK_HEALTH = "SELECT A.*  "
			+ "FROM TBL_CHECK_UP_RECORD A "
			+ "WHERE A.USER_ID = ? "
			+ "AND A.CHILD_ID = ? "
			+ "AND A.MST_CODE = ? ";
	
	public static final String GET_LIST_CHECK_HEALTH = "SELECT A.* "+
			  " FROM TBL_CHECK_UP_RECORD A "+
			  " WHERE A.USER_ID = ? "+
			  " AND A.CHILD_ID = ? ";
	
	public static final String ADD_CHECK_HEALTH_RECORD = "INSERT INTO TBL_CHECK_UP_RECORD "
			+ " ( USER_ID, CHILD_ID, MST_CODE, CREATED_BY, CHECK_UP_DATE, CREATED_DTM, WEIGHT, LENGTH, HEAD_DIAMETER, NOTES ) "
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";
	
	public static final String UPDATE_CHECK_HEALTH_RECORD= "UPDATE TBL_CHECK_UP_RECORD "
			+ " SET WEIGHT = ?, "
			+ " LENGTH = ?, "
			+ " HEAD_DIAMETER = ?, "
			+ " NOTES = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE USER_ID = ? AND CHILD_ID = ? ";

	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CheckUpRecord getCheckUpRecord(int userId, int childId, String mstCode) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CHECK_HEALTH);
		List < CheckUpRecord > result = new ArrayList < CheckUpRecord >();
		try{
			result = jdbcTemplate.query(GET_CHECK_HEALTH, new Object[] { userId, childId, mstCode }, new CheckUpRecordMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List < CheckUpRecord > getListCheckUpRecord(int userId, int childId) throws Exception {
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
	
	@Override
	public boolean addCheckUpRecord(CheckUpRecord checkHealth) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", ADD_CHECK_HEALTH_RECORD);
		int result = 0;
		try {
			result = jdbcTemplate.update(ADD_CHECK_HEALTH_RECORD,
			new Object[] { checkHealth.getUserId(), checkHealth.getChildId(), checkHealth.getMstCode(),
				checkHealth.getCreatedBy(), checkHealth.getCheckUpDate(), checkHealth.getCreatedDtm(),
				checkHealth.getWeight(), checkHealth.getLength(), checkHealth.getHeadDiameter(), checkHealth.getNotes() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean updateCheckUpRecord(CheckUpRecord checkHealth) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_CHECK_HEALTH_RECORD);
		int result = 0;
		try{
			result = jdbcTemplate.update(UPDATE_CHECK_HEALTH_RECORD,
					new Object[] { checkHealth.getWeight(), checkHealth.getLength(), checkHealth.getHeadDiameter(), 
						checkHealth.getNotes(), checkHealth.getUpdatedDtm(), checkHealth.getUpdatedBy(),
						checkHealth.getUserId(), checkHealth.getChildId() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}
	
}
