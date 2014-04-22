package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.wrapper.PageWrapper;

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

		Computer computer = (Computer) sf.getCurrentSession().get(
				Computer.class, id);
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

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer FROM Computer AS computer LEFT OUTER JOIN computer.company AS company ");
		if (!"default".equalsIgnoreCase(pw.getSearch())
				&& !pw.getSearch().equals("")
				&& !"company.name".equalsIgnoreCase(pw.getSearchBy())) {
			sb.append("WHERE computer.name LIKE ").append(
					"'%" + pw.getSearch() + "%' ");
		} else if (!"default".equalsIgnoreCase(pw.getSearch())
				&& !pw.getSearch().equals("")
				&& "company.name".equalsIgnoreCase(pw.getSearchBy())) {
			sb.append("WHERE company.name LIKE ").append(
					"'%" + pw.getSearch() + "%' ");
		}

		if (!"default".equalsIgnoreCase(pw.getOrderBy())) {
			sb.append("ORDER BY ").append(pw.getOrderBy() + " ");
		}
		Query query = sf.getCurrentSession().createQuery(sb.toString())
				.setFirstResult(pw.getOffset())
				.setMaxResults(pw.getComputersPerPage());
		computerList = query.list();

		return computerList;
	}
}