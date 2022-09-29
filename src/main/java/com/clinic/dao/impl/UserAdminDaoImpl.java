package com.clinic.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.clinic.dao.UserAdminDao;
import com.clinic.datamapper.UserAdminMapper;
import com.clinic.entity.UserAdmin;
import com.clinic.config.variable.ApplicationConstant;

@Repository
public class UserAdminDaoImpl implements UserAdminDao {
	
	private static final Logger LOG = LogManager.getLogger(UserAdminDaoImpl.class);

	public static final String GET_ADMIN_USER = "SELECT USER_ID, USERNAME, PASSWORD, STATUS, SESSION_ID, "
			+ " LAST_ACTIVITY, CREATED_DTM, CREATED_BY, LASTUPD_DTM, LASTUPD_BY "
			+ " FROM TBL_USER_ADMIN "
			+ " WHERE USERNAME = ? ";
	
	public static final String UPDATE_USER_ACTIVITY = "UPDATE TBL_USER_ADMIN "
			+ " SET LAST_ACTIVITY = ?, "
			+ " LASTUPD_DTM = ? , "
			+ " LASTUPD_BY = ? "
			+ " WHERE USERNAME = ?";

	public static final String UPDATE_USER_SESSIONID = "UPDATE TBL_USER_ADMIN "
			+ " SET SESSION_ID = ? "
			+ " WHERE USERNAME = ?";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public UserAdmin getAdminByUsername (String username) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_ADMIN_USER);
		List < UserAdmin > result = new ArrayList < UserAdmin >();
		try{
			result = jdbcTemplate.query(GET_ADMIN_USER, new Object[] { username }, new UserAdminMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public boolean updateSessionId (UserAdmin user) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_USER_SESSIONID);
		int result = 0;
		try{ 
			result = jdbcTemplate.update(UPDATE_USER_SESSIONID, 
					new Object[] { user.getSessionId(), user.getUsername() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

	@Override
	public boolean updateLastActivity(UserAdmin user) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_USER_ACTIVITY);
		int result = 0;
		try{ 
			result = jdbcTemplate.update(UPDATE_USER_ACTIVITY, 
					new Object[] { user.getLastActivity(), user.getUpdatedDtm(), 
							user.getUpdatedBy(), user.getUsername() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

}
