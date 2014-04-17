package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.domain.Log;

public class LogRowMapper implements RowMapper<Log> {

	@Override
	public Log mapRow(ResultSet rs, int rowNum) throws SQLException {

		Log log = new Log();
		log.setId(rs.getLong(1));
		log.setDate(new DateTime(rs.getDate(2)));
		log.setType(rs.getString(3));
		log.setDescription(rs.getString(4));
		return log;
	}
}
