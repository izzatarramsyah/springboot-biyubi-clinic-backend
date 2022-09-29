package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.clinic.entity.UserAdmin;
	 
public class UserAdminMapper implements RowMapper<UserAdmin> {
	@Override
	public UserAdmin mapRow(ResultSet rs, int row) throws SQLException {
		UserAdmin result = new UserAdmin();
		result.setUserId(rs.getInt("USER_ID"));
		result.setUsername(rs.getString("USERNAME"));
		result.setPassword(rs.getString("PASSWORD"));
		result.setStatus(rs.getString("STATUS"));
		result.setSessionId(rs.getString("SESSION_ID"));
		result.setLastActivity(rs.getTimestamp("LAST_ACTIVITY"));	
		result.setCreatedDtm(rs.getTimestamp("CREATED_DTM"));
		result.setCreatedBy(rs.getString("CREATED_BY"));
		result.setUpdatedDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setUpdatedBy(rs.getString("LASTUPD_BY"));
		return result;
	}
}

