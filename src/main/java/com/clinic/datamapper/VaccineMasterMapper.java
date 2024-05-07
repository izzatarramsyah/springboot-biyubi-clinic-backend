package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.VaccineMaster;

public class VaccineMasterMapper implements RowMapper<VaccineMaster> {
	@Override
	public VaccineMaster mapRow(ResultSet rs, int row) throws SQLException {
		VaccineMaster result = new VaccineMaster();
		try {
			if (!Objects.isNull(rs.getString("VACCINE_CODE") )) {
				result.setVaccineCode( rs.getString("VACCINE_CODE") );
			}
		} catch (Exception e){
			result.setVaccineCode( "" );
		}
		result.setVaccineName(rs.getString("VACCINE_NAME"));
		result.setVaccineType(rs.getString("VACCINE_TYPE"));
		result.setExpDays(rs.getInt("EXP_DAYS"));
		result.setStatus(rs.getString("STATUS"));
		result.setNotes(rs.getString("NOTES"));
		result.setCreatedDtm(rs.getTimestamp("CREATED_DTM"));
		result.setCreatedBy(rs.getString("CREATED_BY"));
		result.setLastUpdDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setLastUpdBy(rs.getString("LASTUPD_BY"));
		return result;
	}
}
