package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.mapper.LogRowMapper;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDAOImpl implements LogDAO {

	@Autowired
	@Qualifier(value = "DataSource")
	private BoneCPDataSource ds;

	@Autowired
	private JdbcTemplate jt;

	public void create(Log log) {

		jt = new JdbcTemplate(ds);
		String query = "INSERT into log (type, description) VALUES(?,?)";
		jt.update(query, log.getType(), log.getDescription());
	}

	public List<Log> retrieveAll() {

		jt = new JdbcTemplate(ds);
		List<Log> logList = new ArrayList<Log>();
		String query = "SELECT * FROM log";

		logList = jt.query(query, new LogRowMapper());
		return logList;
	}

	public void deleteAll() {

		jt = new JdbcTemplate(ds);
		String query = "DELETE * FROM log";
		jt.update(query);
	}
}
