package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.domain.Log;

public interface LogDAO {

	public void create(Log log);

	public List<Log> retrieveAll();

	public void deleteAll();
}
