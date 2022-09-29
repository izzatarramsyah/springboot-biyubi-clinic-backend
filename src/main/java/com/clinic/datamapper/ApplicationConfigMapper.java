package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.clinic.entity.ApplicationConfig;

public class ApplicationConfigMapper implements RowMapper<ApplicationConfig> {
	@Override
	public ApplicationConfig mapRow(ResultSet rs, int row) throws SQLException {
		ApplicationConfig result = new ApplicationConfig();
		result.setId(rs.getInt("ID"));
		result.setAppName(rs.getString("APP_NAME"));
		result.setServiceName(rs.getString("SVC_NAME"));
		result.setParamName(rs.getString("PARAM_NAME"));
		result.setParamValue(rs.getString("PARAM_VALUE"));
		result.setStatus( rs.getString("STATUS") );
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedBy( rs.getString("CREATED_BY") );
		return result;
	}
	
}