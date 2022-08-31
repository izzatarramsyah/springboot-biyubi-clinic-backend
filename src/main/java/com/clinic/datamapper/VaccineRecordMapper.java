package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.clinic.entity.VaccineRecord;

public class VaccineRecordMapper implements RowMapper<VaccineRecord> {
	@Override
	public VaccineRecord mapRow(ResultSet rs, int row) throws SQLException {
		VaccineRecord result = new VaccineRecord();
		result.setId( rs.getInt("ID") );
		result.setUserId( rs.getInt("USER_ID") );
		result.setChildId( rs.getInt("CHILD_ID") );
		result.setVaccineCode( rs.getString("VACCINE_CODE") );
		result.setBatch( rs.getInt("BATCH") );
		result.setVaccineDate( rs.getDate("VACCINE_DATE") );
		result.setNotes(rs.getString("NOTES"));
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedBy( rs.getString("CREATED_BY") );
		result.setUpdatedDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setUpdatedBy(rs.getString("LASTUPD_BY"));
		return result;
	}
}
