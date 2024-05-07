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
import com.clinic.dao.MasterDao;
import com.clinic.datamapper.CheckUpMasterMapper;
import com.clinic.datamapper.VaccineMasterMapper;
import com.clinic.datamapper.VaccineMstDtlMapper;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineMstDtl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MasterDaoImpl implements MasterDao{
	
	private static final Logger LOG = LogManager.getLogger(MasterDaoImpl.class);

	public static final String GET_MST_VACCINE = "SELECT A.* FROM TBL_VACCINE_MASTER A WHERE A.VACCINE_CODE = ? AND A.STATUS = 'ACTIVE' ";

	public static final String GET_LIST_MST_VACCINE = "SELECT A.* FROM TBL_VACCINE_MASTER A ORDER BY A.VACCINE_CODE ASC";

	public static final String GET_LIST_MST_CHECK_UP = "SELECT A.* FROM TBL_CHECK_UP_MASTER A ORDER BY A.BATCH ASC ";

	public static final String GET_LIST_MST_DTL_VACCINE = "SELECT A.* FROM TBL_VACCINE_MASTER_DTL A "
			+ "WHERE A.VACCINE_CODE = ? ORDER BY A.BATCH ASC ";
	
	public static final String GET_MST_CHECK_UP_BY_CODE = "SELECT A.* FROM TBL_CHECK_UP_MASTER A WHERE A.CODE = ?";

	public static final String GET_MST_CHECK_UP_BY_BATCH = "SELECT A.* FROM TBL_CHECK_UP_MASTER A WHERE A.BATCH = ?";

	public static final String GET_MST_VACCINE_BY_NAME = "SELECT A.* FROM TBL_VACCINE_MASTER A WHERE A.VACCINE_NAME = ? AND A.STATUS = 'ACTIVE' ";

	public static final String COUNT_MST_VACCINE = "SELECT COUNT(*) FROM TBL_VACCINE_MASTER ";

	public static final String ADD_MST_VACCINE = "INSERT INTO TBL_VACCINE_MASTER "
			+ "( VACCINE_CODE, VACCINE_NAME, VACCINE_TYPE, EXP_DAYS, NOTES, STATUS, CREATED_DTM, CREATED_BY ) "
			+ "VALUES (?,?,?,?,?,?,?,?) ";
	
	public static final String ADD_MST_VACCINE_DTL = "INSERT INTO TBL_VACCINE_MASTER_DTL "
			+ " (VACCINE_CODE, BATCH, CREATED_DTM, CREATED_BY) "
			+ " VALUES (?,?,?,?) ";
	
	public static final String UPDATE_MST_VACCINE = "UPDATE TBL_VACCINE_MASTER "
			+ " SET VACCINE_NAME = ?,"
			+ "	VACCINE_TYPE = ?,"
			+ " EXP_DAYS = ?, "
			+ " NOTES = ?,"
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE VACCINE_CODE = ? ";
	
	public static final String CHANGE_STATUS = "UPDATE TBL_VACCINE_MASTER "
			+ " SET STATUS = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE VACCINE_CODE = ? ";
	
	public static final String COUNT_MST_CHECK_UP = "SELECT COUNT(*) FROM TBL_CHECK_UP_MASTER ";

	public static final String ADD_MST_CHECK_UP = "INSERT INTO TBL_CHECK_UP_MASTER "
			+ "( CODE, ACT_NAME, DESCRIPTION, BATCH, NEXT_CHECK_UP_DAYS, STATUS, CREATED_DTM, CREATED_BY ) "
			+ "VALUES (?,?,?,?,?,?,?,?) ";
	
	public static final String UPDATE_MST_CHECK_UP = "UPDATE TBL_CHECK_UP_MASTER "
			+ " SET ACT_NAME = ?, "
			+ "	DESCRIPTION = ?, "
			+ " BATCH = ?, "
			+ " NEXT_CHECK_UP_DAYS = ?, "
			+ " STATUS = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE CODE = ? ";
	
	public static final String UPDATE_STATUS_MST_CHECK_UP = "UPDATE TBL_CHECK_UP_MASTER "
			+ " SET STATUS = ?, "
			+ " LASTUPD_DTM = ?, "
			+ " LASTUPD_BY = ? "
			+ " WHERE CODE = ? ";
	
	@Autowired
	@Qualifier(ApplicationConstant.BEAN_JDBC_CLINIC)
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public VaccineMaster getMstVaccineByCode (String code) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_MST_VACCINE);
		List < VaccineMaster > result = new ArrayList <VaccineMaster>();
		try{
			result = jdbcTemplate.query(GET_MST_VACCINE, new Object[] { code }, new VaccineMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() == 0 ? null : result.get(0);
	}
	
	@Override
	public List < VaccineMaster > getListMstVaccine () throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_VACCINE);
		List < VaccineMaster > result = new ArrayList <VaccineMaster>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_VACCINE, new Object[] {}, new VaccineMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	@Override
	public List < VaccineMstDtl > getListVaccineMstDtl(String vaccineCode) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_DTL_VACCINE);
		List < VaccineMstDtl > result = new ArrayList <VaccineMstDtl>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_DTL_VACCINE, new Object[] { vaccineCode }, new VaccineMstDtlMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}
	
	@Override
	public List<CheckUpMaster> getListMstCheckUp() throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_LIST_MST_CHECK_UP);
		List < CheckUpMaster > result = new ArrayList <CheckUpMaster>();
		try{
			result = jdbcTemplate.query(GET_LIST_MST_CHECK_UP, new Object[] { }, new CheckUpMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}
	
	@Override
	public CheckUpMaster getMstCheckUpByCode(String code) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_MST_CHECK_UP_BY_CODE);
		List < CheckUpMaster > result = new ArrayList <CheckUpMaster>();
		try{
			result = jdbcTemplate.query(GET_MST_CHECK_UP_BY_CODE, new Object[] { code }, new CheckUpMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() == 0 ? null : result.get(0);

	}

	@Override
	public CheckUpMaster getListMstCheckUpByBatch(int batch) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_MST_CHECK_UP_BY_BATCH);
		List < CheckUpMaster > result = new ArrayList <CheckUpMaster>();
		try{
			result = jdbcTemplate.query(GET_MST_CHECK_UP_BY_BATCH, new Object[] { batch }, new CheckUpMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() == 0 ? null : result.get(0);
	}
	
	@Override
	public VaccineMaster getMstVaccineByName(String code) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", GET_MST_VACCINE_BY_NAME);
		List < VaccineMaster > result = new ArrayList <VaccineMaster>();
		try{
			result = jdbcTemplate.query(GET_MST_VACCINE_BY_NAME, new Object[] { code }, new VaccineMasterMapper());
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result.size() == 0 ? null : result.get(0);
	}

	@Override
	public int countVaccineMaster() throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", COUNT_MST_VACCINE);
		Integer result = 0;
		try{
			result = jdbcTemplate.queryForObject(COUNT_MST_VACCINE, new Object[] {}, Integer.class);
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	@Override
	public boolean addVaccineMaster(VaccineMaster vaccineMaster) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", ADD_MST_VACCINE);
		int result = 0;
		try{			
			result = jdbcTemplate.update(ADD_MST_VACCINE,
					new Object[] { vaccineMaster.getVaccineCode(), vaccineMaster.getVaccineName(), vaccineMaster.getVaccineType(),
							vaccineMaster.getExpDays(), vaccineMaster.getNotes(), vaccineMaster.getStatus(), 
							vaccineMaster.getCreatedDtm(), vaccineMaster.getCreatedBy() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean addVaccineMstDtl(VaccineMstDtl vaccineMstDtl) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", ADD_MST_VACCINE_DTL);
		int result = 0;
		try{			
			result = jdbcTemplate.update(ADD_MST_VACCINE_DTL,
					new Object[] { vaccineMstDtl.getVaccineCode(), vaccineMstDtl.getBatch(),
							vaccineMstDtl.getCreatedDtm(), vaccineMstDtl.getCreatedBy() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean updateVaccineMaster(VaccineMaster vaccineMaster) throws Exception {
		LOG.traceEntry();
		LOG.info("SQL::{}", UPDATE_MST_VACCINE);
		int result = 0;
		try{			
			result = jdbcTemplate.update(UPDATE_MST_VACCINE,
					new Object[] { vaccineMaster.getVaccineName(), vaccineMaster.getVaccineType(),  
							vaccineMaster.getExpDays(), vaccineMaster.getNotes(),
							vaccineMaster.getLastUpdDtm(), vaccineMaster.getLastUpdBy(), vaccineMaster.getVaccineCode() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean changeStatusVaccineMaster(VaccineMaster vaccineMaster) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", CHANGE_STATUS);
		int result = 0;
		try{			
			result = jdbcTemplate.update(CHANGE_STATUS,
					new Object[] { vaccineMaster.getStatus(), vaccineMaster.getLastUpdDtm(), vaccineMaster.getLastUpdBy() , vaccineMaster.getVaccineCode()});
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public int countVaccineCheckUp() throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", COUNT_MST_CHECK_UP);
		Integer result = 0;
		try{
			result = jdbcTemplate.queryForObject(COUNT_MST_CHECK_UP, new Object[] {}, Integer.class);
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage()); 
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result;
	}

	@Override
	public boolean addCheckUpMaster(CheckUpMaster checkUpMaster) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", ADD_MST_CHECK_UP);
		int result = 0;
		try{	
			result = jdbcTemplate.update(ADD_MST_CHECK_UP,
					new Object[] { checkUpMaster.getCode(), checkUpMaster.getActName(), checkUpMaster.getDescription(),
							checkUpMaster.getBatch(), checkUpMaster.getNextCheckUpDays(), checkUpMaster.getStatus(),
							checkUpMaster.getCreatedDtm(), checkUpMaster.getCreatedBy() });
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean updateCheckUpMaster(CheckUpMaster checkUpMaster) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_MST_CHECK_UP);
		LOG.info(new ObjectMapper().writer()
                .writeValueAsString( checkUpMaster ));
		int result = 0;
		try{	
			result = jdbcTemplate.update(UPDATE_MST_CHECK_UP,
					new Object[] { checkUpMaster.getActName(), checkUpMaster.getDescription(), checkUpMaster.getBatch(), 
							checkUpMaster.getNextCheckUpDays(),  checkUpMaster.getStatus(), checkUpMaster.getLastUpdDtm(), checkUpMaster.getLastUpdBy(),
							checkUpMaster.getCode()});
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}

	@Override
	public boolean changeStatusCheckUpMaster(CheckUpMaster checkUpMaster) throws Exception {
		LOG.traceEntry();
		LOG.debug("SQL::{}", UPDATE_STATUS_MST_CHECK_UP);
		int result = 0;
		try{	
			result = jdbcTemplate.update(UPDATE_STATUS_MST_CHECK_UP,
					new Object[] { checkUpMaster.getStatus(), checkUpMaster.getLastUpdDtm(), 
							checkUpMaster.getLastUpdBy(), checkUpMaster.getCode()});
		}catch (Exception e){
			LOG.error("ERR :: {}", e.getMessage());
		}
		LOG.debug("RESULT::{}", result);
		LOG.traceExit();
		return result == 0 ? false : true; 
	}
	
}
