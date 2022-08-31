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
import com.clinic.dao.UserDao;
import com.clinic.datamapper.ChildMapper;
import com.clinic.datamapper.UserMapper;
import com.clinic.entity.Child;
import com.clinic.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);
	
	public static final String GET_USER_BY_USERNAME = "SELECT A.*  FROM TBL_USER A "
			+ " WHERE A.USERNAME = ? ";
	
	public static final String GET_USER_BY_FULLNAME = "SELECT A.* FROM TBL_USER A "
			+ " WHERE A.FULLNAME = ? ";
	
	public static final String GET_USER_BY_ID = "SELECT A.* FROM TBL_USER A "
			+ " WHERE A.ID = ? ";
	
	public static final String GET_CHILD_BY_USER_ID = "SELECT A.* FROM TBL_CHILD A "
			+ " WHERE A.USER_ID = ? ";
	
	public static final String GET_CHILD_BY_ID = "SELECT  A.* FROM TBL_CHILD A "
			+ " WHERE A.ID = ? ";
	
	public static final String UPDATE_LAST_ACTIVITY = "UPDATE TBL_USER "
			+ "SET LAST_ACTIVITY = ? "
			+ "WHERE ID = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User getUserByFullname(String fullname) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_USER_BY_FULLNAME);
		List < User > result = new ArrayList < User >();
		try{
			result = jdbcTemplate.query(GET_USER_BY_FULLNAME, new Object[] { fullname }, new UserMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List < Child > getChildByUserID(int id) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CHILD_BY_USER_ID);
		List < Child > result = new ArrayList < Child >();
		try{
			result = jdbcTemplate.query(GET_CHILD_BY_USER_ID, new Object[] { id }, new ChildMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result; 
	}

	@Override
	public User getUserByUsername(String username) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_USER_BY_USERNAME);
		List < User > result = new ArrayList < User >();
		try{
			result = jdbcTemplate.query(GET_USER_BY_USERNAME, new Object[] { username }, new UserMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}
	
	@Override
	public User getUserByID(int id) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_USER_BY_ID);
		List < User > result = new ArrayList < User >();
		try{
			result = jdbcTemplate.query(GET_USER_BY_ID, new Object[] { id }, new UserMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null; 
	}

	@Override
	public Child getChildByID(int id) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CHILD_BY_ID);
		List < Child > result = new ArrayList < Child >();
		try{
			result = jdbcTemplate.query(GET_CHILD_BY_ID, new Object[] { id }, new ChildMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : new Child(); 
	}

	@Override
	public boolean updateLastActivity(User user) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_LAST_ACTIVITY);
		int result = 0;
		try{
			result = jdbcTemplate.update(UPDATE_LAST_ACTIVITY,
					new Object[] { user.getLastActivity(), user.getId() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}
	
}
