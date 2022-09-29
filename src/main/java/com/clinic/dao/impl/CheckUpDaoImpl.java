package com.clinic.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
			  " FROM TBL_CHECK_UP_RECORD_DTL A "+
			  " WHERE A.MST_CODE = ? "+
			  " AND A.REC_ID = ? ";
	
	public static final String GET_LIST_CHECK_HEALTH = "SELECT A.* "+
			  " FROM TBL_CHECK_UP_RECORD A "+
			  " WHERE A.USER_ID = ? "+
			  " AND A.CHILD_ID = ? ";
	
	public static final String ADD_CHECK_HEALTH_RECORD = "INSERT INTO TBL_CHECK_UP_RECORD "
			+ " ( USER_ID, CHILD_ID, MST_CODE, CREATED_BY, CHECK_UP_DATE, CREATED_DTM ) "
			+ " VALUES (?,?,?,?,?,current_timestamp) ";
	
	public static final String INSERT_GROWTH_DTL = "INSERT INTO TBL_CHECK_UP_RECORD_DTL "
			+ "( MST_CODE, REC_ID, WEIGHT, LENGTH, HEAD_DIAMETER, NOTES, CREATED_DTM, CREATED_BY ) "
			+ "VALUES (?,?,?,?,?,?,?,?) ";
	
	public static final String UPDATE_GROWTH_DTL = "UPDATE TBL_CHECK_UP_RECORD_DTL "
			+ " SET WEIGHT = ?, "
			+ " LENGTH = ?, "
			+ " HEAD_DIAMETER = ?, "
			+ " NOTES = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE REC_ID = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CheckUpRecord getCheckUpRecord(int userId, int childId, String mstCode) throws Exception {
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
	public int addCheckUpRecord(CheckUpRecord checkHealth) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", ADD_CHECK_HEALTH_RECORD);
		int id = 0;
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
						PreparedStatement pst = connection.prepareStatement(ADD_CHECK_HEALTH_RECORD, Statement.RETURN_GENERATED_KEYS);
						pst.setInt(1, checkHealth.getUserId());
						pst.setInt(2, checkHealth.getChildId());
						pst.setString(3, checkHealth.getMstCode());
						pst.setString(4, checkHealth.getCreatedBy());
						pst.setDate(5, new java.sql.Date(checkHealth.getCheckUpDate().getTime()));
						return pst;
					}, keyHolder);
			if (keyHolder.getKeys().size() > 1) {
				id = (int)keyHolder.getKeys().get("ID");
			} 
		}catch (Exception e){
			id = 0;
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", id);
		LOG.traceExit();
		return id;
	}

	@Override
	public boolean addGrowthDtl(GrowthDtl growthDtl) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", INSERT_GROWTH_DTL);
		int result = 0;
		try{
			result = jdbcTemplate.update(INSERT_GROWTH_DTL,
					new Object[] { growthDtl.getMstCode(), growthDtl.getRecId(), growthDtl.getWeight(),
							growthDtl.getLength(), growthDtl.getHeadDiameter(), growthDtl.getNotes(),
							growthDtl.getCreatedDtm(), growthDtl.getCreatedBy() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean updateGrowthDtl(GrowthDtl growthDtl) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_GROWTH_DTL);
		int result = 0;
		try{
			result = jdbcTemplate.update(UPDATE_GROWTH_DTL,
					new Object[] { growthDtl.getWeight(), growthDtl.getLength(), growthDtl.getHeadDiameter(), 
							growthDtl.getNotes(), growthDtl.getCreatedDtm(), growthDtl.getCreatedBy(),
							growthDtl.getRecId() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}
	
}
