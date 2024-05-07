package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.User;

public class UserMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int row) throws SQLException {
		User result = new User();
		result.setId(rs.getInt("ID"));
		result.setUsername(rs.getString("USERNAME"));
		result.setPassword(rs.getString("PASSWORD"));
		result.setFullname(rs.getString("FULLNAME"));
		result.setAddress(rs.getString("ADDRESS"));
		result.setEmail(rs.getString("EMAIL"));
		result.setPhone_no(rs.getString("PHONE_NO"));
		result.setStatus(rs.getString("STATUS"));
		result.setLastActivity(rs.getDate("LAST_ACTIVITY"));	
		result.setCreatedDtm(rs.getTimestamp("CREATED_DTM"));
		result.setCreatedBy(rs.getString("CREATED_BY"));
		result.setUpdatedDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setUpdatedBy(rs.getString("LASTUPD_BY"));
		return result;
	}
}