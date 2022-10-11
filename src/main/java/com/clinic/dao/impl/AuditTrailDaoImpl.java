package com.clinic.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clinic.config.variable.ApplicationConstant;
import com.clinic.dao.AuditTrailDao;
import com.clinic.datamapper.AuditTrailMapper;
import com.clinic.entity.AuditTrail;

@Repository
public class AuditTrailDaoImpl implements AuditTrailDao{

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
	public boolean saveAuditTrail(AuditTrail auditTrail) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", INSERT_AUDIT_TRAIL);
		int result = 0;
		try{
			result = jdbcTemplate.update(INSERT_AUDIT_TRAIL,
					new Object[] { auditTrail.getActivity(), auditTrail.getKey(), auditTrail.getValue1(),
							auditTrail.getValue2(), auditTrail.getStatus(), auditTrail.getCreatedDtm(), auditTrail.getCreatedBy() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public List < AuditTrail > getAuditTrail(String username, Date startDate, Date endDate) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_AUDIT_TRAIL);
		List < AuditTrail > result = new ArrayList < AuditTrail >();
		try{
			result = jdbcTemplate.query(GET_AUDIT_TRAIL, new Object[] { username, startDate, endDate }, new AuditTrailMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

}
