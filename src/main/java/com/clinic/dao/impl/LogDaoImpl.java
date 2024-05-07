package com.clinic.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clinic.config.variable.ApplicationConstant;
import com.clinic.dao.LogDao;
import com.clinic.datamapper.LogMapper;
import com.clinic.entity.Log;

@Repository
public class LogDaoImpl implements LogDao{

	private static final Logger LOG = LogManager.getLogger(CheckUpDaoImpl.class);

	public static final String INSERT_AUDIT_TRAIL = "INSERT INTO TBL_APP_LOGS "+
			  " ( ACTIVITY, KEY, VALUE1, VALUE2, STATUS, CREATED_DTM, CREATED_BY ) "+
			  " VALUES (?,?,?,?,?,?,?)";
	
	public static final String GET_AUDIT_TRAIL = "SELECT A.* FROM TBL_APP_LOGS A "
			+ "WHERE A.VALUE1 = ? AND A.CREATED_DTM  between ? and ? ORDER BY A.CREATED_DTM ASC";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean saveLog(Log log) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", INSERT_AUDIT_TRAIL);
		int result = 0;
		try{
			result = jdbcTemplate.update(INSERT_AUDIT_TRAIL,
					new Object[] { log.getActivity(), log.getKey(), log.getValue1(),
							log.getValue2(), log.getStatus(), log.getCreatedDtm(), log.getCreatedBy() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public List < Log > getLogs(String username) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_AUDIT_TRAIL);
		List < Log > result = new ArrayList < Log >();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
        Date today = new Date();
        endDate.setTime( today );
        endDate.add( Calendar.DATE, 1 );
		startDate.setTime( today );
        startDate.add( Calendar.DATE, -7 );

		try{
			result = jdbcTemplate.query(GET_AUDIT_TRAIL, new Object[] { username, startDate , endDate }, new LogMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

}
