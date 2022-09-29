package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.AuditTrail;

public class AuditTrailMapper implements RowMapper<AuditTrail> {
	@Override
	public AuditTrail mapRow(ResultSet rs, int row) throws SQLException {
		AuditTrail result = new AuditTrail();
		result.setId(rs.getLong("ID"));
		result.setActivity(rs.getString("ACTIVITY"));
		result.setKey(rs.getString("KEY"));
		result.setValue1(rs.getString("VALUE1"));
		result.setValue2(rs.getString("VALUE2"));
		result.setStatus( rs.getString("STATUS") );
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedBy( rs.getString("CREATED_BY") );
		return result;
	}
	
}