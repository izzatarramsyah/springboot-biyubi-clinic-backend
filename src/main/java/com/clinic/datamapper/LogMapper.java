package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.Log;

public class LogMapper implements RowMapper<Log> {
	@Override
	public Log mapRow(ResultSet rs, int row) throws SQLException {
		Log result = new Log();
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