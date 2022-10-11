package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.CheckUpMaster;

public class CheckUpMasterMapper implements RowMapper<CheckUpMaster> {
	@Override
	public CheckUpMaster mapRow(ResultSet rs, int row) throws SQLException {
		CheckUpMaster result = new CheckUpMaster();
		result.setCode(rs.getString("CODE"));
		result.setActName(rs.getString("ACT_NAME"));
		result.setDescription(rs.getString("DESCRIPTION"));
		result.setBatch(rs.getInt("BATCH"));
		result.setNextCheckUpDays(rs.getInt("NEXT_CHECK_UP_DAYS"));
		result.setStatus(rs.getString("STATUS"));
		result.setCreatedDtm(rs.getTimestamp("CREATED_DTM"));
		result.setCreatedBy(rs.getString("CREATED_BY"));
		result.setLastUpdDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setLastUpdBy(rs.getString("LASTUPD_BY"));
		return result;
	}
	
}
