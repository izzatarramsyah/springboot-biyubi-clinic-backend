package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.Child;

public class ChildMapper implements RowMapper<Child> {
	@Override
	public Child mapRow(ResultSet rs, int row) throws SQLException {
		Child result = new Child();
		result.setId(rs.getInt("ID"));
		result.setUserId(rs.getInt("USER_ID"));
		result.setFullname(rs.getString("FULLNAME"));
		result.setBirthDate(rs.getDate("BIRTH_DATE"));
		result.setGender(rs.getString("GENDER"));
		result.setNotes(rs.getString("NOTES"));
		result.setCreatedDtm(rs.getTimestamp("CREATED_DTM"));
		result.setCreatedBy(rs.getString("CREATED_BY"));
		result.setUpdatedDtm(rs.getTimestamp("LASTUPD_DTM"));
		result.setUpdatedBy(rs.getString("LASTUPD_BY"));
		return result;
	}
}
