package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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

	@SuppressWarnings("unchecked")
	public List<Computer> retrieveAll(PageWrapper pw) {

		List<Computer> computerList = new ArrayList<Computer>();

		Criteria criteria = sf.getCurrentSession().createCriteria(
				Computer.class);
		criteria.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);

		if (!pw.getSearch().equals("")
				&& !"company".equalsIgnoreCase(pw.getSearchBy())) {
			StringBuilder sb = new StringBuilder("%").append(pw.getSearch())
					.append("%");
			criteria.add(Restrictions.like("name", sb.toString()));
		} else if ("company".equalsIgnoreCase(pw.getSearchBy())) {
			StringBuilder sb = new StringBuilder("%").append(pw.getSearch())
					.append("%");
			criteria.add(Restrictions.like("company.name", sb.toString()));
		}

		if (!"default".equalsIgnoreCase(pw.getOrderBy())) {
			criteria.addOrder(Property.forName(pw.getOrderBy()).asc());
		}
		criteria.setFirstResult(pw.getOffset());
		criteria.setMaxResults(pw.getComputersPerPage());
		computerList = criteria.list();

		return computerList;
	}
}