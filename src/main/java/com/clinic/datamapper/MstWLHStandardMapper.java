package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.MstWLHStandard;

public class MstWLHStandardMapper implements RowMapper< MstWLHStandard > {
	@Override
	public MstWLHStandard mapRow(ResultSet rs, int row) throws SQLException {
		MstWLHStandard result = new MstWLHStandard();
		result.setType(rs.getString("TYPE"));
		result.setMonth(rs.getInt("MONTH"));
		result.setValue(rs.getDouble("VALUE"));
		result.setCategory(rs.getString("CATEGORY"));
		return result;
	}
}