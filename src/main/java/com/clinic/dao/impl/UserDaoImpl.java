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
import com.clinic.dao.UserDao;
import com.clinic.datamapper.ChildMapper;
import com.clinic.datamapper.UserMapper;
import com.clinic.entity.Child;
import com.clinic.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);
	
	public static final String INSERT_USER = "INSERT INTO TBL_USER "
			+ " ( USERNAME, PASSWORD, FULLNAME, ADDRESS, EMAIL, PHONE_NO, STATUS, CREATED_DTM, CREATED_BY, JOIN_DATE ) "
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";
	 
	public static final String INSERT_CHILD = "INSERT INTO TBL_CHILD "
			+ " ( USER_ID, FULLNAME, BIRTH_DATE, GENDER, NOTES, CREATED_DTM, CREATED_BY )"
			+ " VALUES (?,?,?,?,?,?,?) ";
	
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

	public static final String GET_CHILD_BY_FULLNAME = "SELECT  A.* FROM TBL_CHILD A "
			+ " WHERE A.FULLNAME = ? ";
	
	public static final String UPDATE_LAST_ACTIVITY = "UPDATE TBL_USER "
			+ "SET LAST_ACTIVITY = ? "
			+ "WHERE ID = ? ";
	
	public static final String GET_USER = "SELECT A.* FROM TBL_USER A ";

	public static final String UPDATE_USER = "UPDATE TBL_USER "
			+ " SET FULLNAME = ?, "
			+ " ADDRESS = ?, "
			+ " EMAIL = ?, "
			+ " PHONE_NO = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE ID = ? ";
	
	public static final String CHANGE_STATUS_USER = "UPDATE TBL_USER "
			+ " SET STATUS = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE ID = ? ";
	
	public static final String UPDATE_CHILD = "UPDATE TBL_CHILD "
			+ " SET GENDER = ?,"
			+ " FULLNAME = ?,"
			+ " BIRTH_DATE = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE ID = ? ";
	
	public static final String CHANGE_PASSWORD = "UPDATE TBL_USER "
			+ " SET PASSWORD = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE USERNAME = ? ";
	
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
	public Child getChildByFullname(String fullname) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_CHILD_BY_FULLNAME);
		List < Child > result = new ArrayList < Child >();
		try{
			result = jdbcTemplate.query(GET_CHILD_BY_FULLNAME, new Object[] { fullname }, new ChildMapper());
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
	
	@Override
	public List<User> getUser() throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_USER);
		List < User > result = new ArrayList < User >();
		try{
			result = jdbcTemplate.query(GET_USER, new Object[] {}, new UserMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	@Override
	public boolean insertUser(User user) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", INSERT_USER);
		int result = 0;
		try{
			result = jdbcTemplate.update(INSERT_USER,
					new Object[] {user.getUsername(), user.getPassword(), user.getFullname(), 
							user.getAddress(), user.getEmail(), user.getPhone_no(), user.getStatus(), 
							user.getCreatedDtm(), user.getCreatedBy(), user.getJoinDate()});
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

	@Override
	public int insertChild(Child child) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", INSERT_CHILD);
		int id = 0;
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
						PreparedStatement pst = connection.prepareStatement(INSERT_CHILD, Statement.RETURN_GENERATED_KEYS);
						pst.setInt(1, child.getUserId());
						pst.setString(2, child.getFullname());
						pst.setDate(3, new java.sql.Date(child.getBirthDate().getTime()));
						pst.setString(4, child.getGender());
						pst.setString(5, child.getNotes());
						pst.setDate(6, new java.sql.Date(child.getCreatedDtm().getTime()));
						pst.setString(7, child.getCreatedBy());
						return pst;
					}, keyHolder);
			if (keyHolder.getKeys().size() > 1) {
				id = (int)keyHolder.getKeys().get("ID");
			} 
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", id);
		LOG.traceExit();
		return id;
	}
	
	@Override 
	public boolean updateUser(User user) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_USER);
		int result = 0;
		try{
			result = jdbcTemplate.update(UPDATE_USER,
					new Object[] { user.getFullname(), user.getAddress(), user.getEmail(), 
							user.getPhone_no(),  user.getUpdatedDtm(), user.getUpdatedBy(), 
							user.getId() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

	@Override
	public boolean updateChild(Child child) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_CHILD);
		int result = 0;
		try{
			result = jdbcTemplate.update(UPDATE_CHILD,
					new Object[] { child.getGender(), child.getFullname(), child.getBirthDate(), 
							child.getUpdatedDtm(), child.getUpdatedBy(), child.getId() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

	@Override
	public boolean changeStatusUser(User user) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", CHANGE_STATUS_USER);
		int result = 0;
		try{
			result = jdbcTemplate.update(CHANGE_STATUS_USER,
					new Object[] { user.getStatus(), user.getUpdatedDtm(), user.getUpdatedBy(), user.getId() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

	@Override
	public boolean changePassword(User user, String newPassword) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", CHANGE_PASSWORD);
		int result = 0;
		try{
			result = jdbcTemplate.update(CHANGE_PASSWORD,
					new Object[] { newPassword, user.getUpdatedDtm(), user.getUpdatedBy(), user.getUsername() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true;
	}

}
