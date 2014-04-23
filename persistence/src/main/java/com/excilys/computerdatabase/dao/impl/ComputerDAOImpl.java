package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.QCompany;
import com.excilys.computerdatabase.domain.QComputer;
import com.excilys.computerdatabase.wrapper.PageWrapper;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class ComputerDAOImpl implements ComputerDAO {

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	private SessionFactory sf;

	public void create(Computer c) {

		sf.getCurrentSession().persist(c);
	}

	public Computer retrieveById(Long id) {

		/*
		 * Computer computer = (Computer) sf.getCurrentSession().get(
		 * Computer.class, id); return computer;
		 */
		QComputer qcomputer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(sf.getCurrentSession());
		Computer computer = query.from(qcomputer).where(qcomputer.id.eq(id))
				.uniqueResult(qcomputer);
		return computer;
	}

	public void update(Computer c) {

		sf.getCurrentSession().merge(c);

	}

	public void delete(Long id) {

		Computer computer = retrieveById(id);
		sf.getCurrentSession().delete(computer);

	}

	public List<Computer> retrieveAll(PageWrapper pw) {

		List<Computer> computerList = new ArrayList<Computer>();

		HibernateQuery query = new HibernateQuery(sf.getCurrentSession());
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		query.from(computer).leftJoin(computer.company, company);
		if (!pw.getSearch().equals("")
				&& !"company".equalsIgnoreCase(pw.getSearchBy())) {
			query.where(computer.name.containsIgnoreCase(pw.getSearch()));
		} else if ("company".equalsIgnoreCase(pw.getSearchBy())) {
			query.where(company.name.containsIgnoreCase(pw.getSearch()));
		}
		switch (pw.getOrderBy()) {
		case "id":
			query.orderBy(computer.id.asc());
			break;
		case "name":
			query.orderBy(computer.name.asc());
			break;
		case "introduced":
			query.orderBy(computer.introduced.asc());
			break;
		case "disontinued":
			query.orderBy(computer.discontinued.asc());
			break;
		case "company.id":
			query.orderBy(company.id.asc());
			break;
		case "company.name":
			query.orderBy(company.name.asc());
			break;
		}
		query.offset(pw.getOffset());
		query.limit(pw.getComputersPerPage());
		computerList = query.list(computer);
		return computerList;
	}
}