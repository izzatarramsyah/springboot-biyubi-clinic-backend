package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.CheckUpRecord;

public class CheckUpRecordMapper implements RowMapper<CheckUpRecord> {
	@Override
	public CheckUpRecord mapRow(ResultSet rs, int row) throws SQLException {
		CheckUpRecord result = new CheckUpRecord();
		result.setId( rs.getInt("ID") );
		result.setUserId( rs.getInt("USER_ID") );
		result.setMstCode( rs.getString("MST_CODE") );
		result.setCheckUpDate( rs.getDate("CHECK_UP_DATE") );
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedBy( rs.getString("CREATED_BY") );
		result.setUpdatedDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setUpdatedBy(rs.getString("LASTUPD_BY"));
		result.setWeight( rs.getInt("WEIGHT") );
		result.setLength( rs.getInt("LENGTH") );
		result.setHeadDiameter( rs.getInt("HEAD_DIAMETER") );
		result.setNotes(rs.getString("NOTES"));
		return result;
	}
}
