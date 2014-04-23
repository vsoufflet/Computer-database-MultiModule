package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.domain.QLog;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class LogDAOImpl implements LogDAO {

	@Autowired
	private SessionFactory sf;

	public void create(Log log) {

		sf.getCurrentSession().persist(log);
	}

	public List<Log> retrieveAll() {

		List<Log> logList = new ArrayList<Log>();
		QLog log = QLog.log;
		HibernateQuery query = new HibernateQuery(sf.getCurrentSession());
		
		logList = query.list(log);
		
		return logList;
	}

	public void deleteAll() {

		List<Log> logList = retrieveAll();
		for (Log log : logList) {
			sf.getCurrentSession().delete(log);
		}
	}
}
