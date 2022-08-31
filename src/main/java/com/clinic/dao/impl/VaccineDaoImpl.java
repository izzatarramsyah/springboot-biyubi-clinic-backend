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
import com.clinic.dao.VaccineDao;
import com.clinic.datamapper.VaccineRecordMapper;
import com.clinic.entity.VaccineRecord;

@Repository
public class VaccineDaoImpl implements VaccineDao{ 

	private static final Logger LOG = LogManager.getLogger(VaccineDaoImpl.class);

	public static final String GET_VACCINE_RECORD = "SELECT A.* FROM TBL_VACCINE_RECORD A "
			+ " WHERE A.USER_ID = ? "
			+ " AND A.CHILD_ID = ? "
			+ " AND A.VACCINE_CODE = ? "
			+ " AND A.BATCH = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public VaccineRecord getVaccineRecord (int userId, int childId, int batch, String vaccineCode) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_VACCINE_RECORD);
		List < VaccineRecord > result = new ArrayList < VaccineRecord >();
		try{
			result = jdbcTemplate.query(GET_VACCINE_RECORD, new Object[] { userId, childId, vaccineCode, batch }, 
					new VaccineRecordMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() > 0 ? result.get(0) : null;
	}

}
