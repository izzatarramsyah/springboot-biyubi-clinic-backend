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
import com.clinic.dao.ApplicationConfigDao;
import com.clinic.datamapper.ApplicationConfigMapper;
import com.clinic.entity.ApplicationConfig;

@Repository
public class ApplicationDaoImpl implements ApplicationConfigDao {
	
	private static final Logger LOG = LogManager.getLogger(CheckUpDaoImpl.class);

	public static final String GET_APP_CONFIG = "SELECT A.* FROM TBL_APP_CONFIG A "
			+ "WHERE A.APP_NAME = ? AND A.SVC_NAME = ? AND A.STATUS = 'ACTIVE' ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ApplicationConfig> get(String appsName, String serviceName) {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_APP_CONFIG);
		List < ApplicationConfig > result = new ArrayList < ApplicationConfig >();
		try{
			result = jdbcTemplate.query(GET_APP_CONFIG, new Object[] { appsName, serviceName }, new ApplicationConfigMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

}
