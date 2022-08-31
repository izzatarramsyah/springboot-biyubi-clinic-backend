package com.clinic.datamapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.springframework.jdbc.core.RowMapper;

import com.clinic.entity.GrowthDtl;
import com.clinic.util.Util;

public class GrowthDtlMapper implements RowMapper<GrowthDtl> {
	@Override
	public GrowthDtl mapRow(ResultSet rs, int row) throws SQLException {
		GrowthDtl result = new GrowthDtl();
		result.setMstCode(rs.getString("MST_CODE"));
		result.setRecId(rs.getInt("REC_ID"));
		result.setWeight( rs.getInt("WEIGHT") );
		result.setLength( rs.getInt("LENGTH") );
		result.setHeadDiameter( rs.getInt("HEAD_DIAMETER") );
		result.setNotes(rs.getString("NOTES"));
		result.setCreatedDtm( rs.getTimestamp("CREATED_DTM") );
		result.setCreatedBy( rs.getString("CREATED_BY") );
		result.setLastUpdDtm( rs.getTimestamp("LASTUPD_DTM") );
		result.setLastUpdBy( rs.getString("LASTUPD_BY") );
		return result;
	}
}
