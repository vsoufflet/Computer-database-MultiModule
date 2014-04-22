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

		/*
		 * String query =
		 * "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)"
		 * ; Date introduced = null; Date discontinued = null; Long companyId =
		 * null;
		 * 
		 * if (c.getIntroduced() != null) { introduced = new
		 * Date(c.getIntroduced().toDate().getTime()); } if (c.getDiscontinued()
		 * != null) { discontinued = new
		 * Date(c.getDiscontinued().toDate().getTime()); } if (c.getCompany() !=
		 * null) { companyId = c.getCompany().getId(); } jt.update(query,
		 * c.getName(), introduced, discontinued, companyId);
		 */

		sf.getCurrentSession().persist(c);
	}

	public Computer retrieveById(Long id) {

		/*
		 * String query = "SELECT * FROM computer WHERE id=?";
		 * 
		 * Computer computer = jt.queryForObject(query, new Object[] { id }, new
		 * ComputerRowMapper());
		 * 
		 * if (computer.getCompany() != null) { Company company =
		 * companyDAO.retrieveById(computer.getCompany() .getId());
		 * computer.setCompany(company); }
		 */
		Computer computer = (Computer) sf.getCurrentSession().get(
				Computer.class, id);
		return computer;
	}

	public void update(Computer c) {

		/*
		 * String query =
		 * "UPDATE computer SET (name, introduced, discontinued, company_id) VALUES(?,?,?,?) WHERE id=?"
		 * ; Date introduced = null; Date discontinued = null; Long companyId =
		 * null;
		 * 
		 * Computer c = retrieveById(id); if (c.getIntroduced() != null) {
		 * introduced = new Date(c.getIntroduced().toDate().getTime()); } if
		 * (c.getDiscontinued() != null) { discontinued = new
		 * Date(c.getDiscontinued().toDate().getTime()); } if (c.getCompany() !=
		 * null) { companyId = c.getCompany().getId(); } jt.update(query, new
		 * Object[] { c.getName(), introduced, discontinued, companyId,
		 * c.getId() });
		 */

		sf.getCurrentSession().merge(c);

	}

	public void delete(Long id) {

		/*
		 * String query = "DELETE FROM computer WHERE id=?"; Computer c =
		 * retrieveById(id); jt.update(query, c.getId());
		 */
		Computer computer = retrieveById(id);
		sf.getCurrentSession().delete(computer);
	}

	public List<Computer> retrieveAll(PageWrapper pw) {

		/*
		 * List<Computer> computerList = new ArrayList<Computer>(); // String
		 * numberOfLines = "SELECT FOUND_ROWS()";
		 * 
		 * StringBuilder sb = new StringBuilder(); sb.append(
		 * "SELECT SQL_CALC_FOUND_ROWS * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
		 * ); if (!"default".equalsIgnoreCase(pw.getSearch()) &&
		 * !pw.getSearch().equals("") &&
		 * !"company.name".equalsIgnoreCase(pw.getSearchBy())) {
		 * sb.append("WHERE computer.name LIKE ").append( "'%" + pw.getSearch()
		 * + "%' "); } else if (!"default".equalsIgnoreCase(pw.getSearch()) &&
		 * !pw.getSearch().equals("") &&
		 * "company.name".equalsIgnoreCase(pw.getSearchBy())) {
		 * sb.append("WHERE company.name LIKE ").append( "'%" + pw.getSearch() +
		 * "%' "); }
		 * 
		 * if (!"default".equalsIgnoreCase(pw.getOrderBy())) {
		 * sb.append("ORDER BY ").append(pw.getOrderBy() + " "); }
		 * sb.append("LIMIT ").append(pw.getOffset() + ",")
		 * .append(pw.getComputersPerPage()); String query = sb.toString();
		 * computerList = jt.query(query, new ComputerRowMapper());
		 * 
		 * return computerList;
		 */
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
		/*
		 * sb.append("LIMIT ").append(pw.getOffset() + ",")
		 * .append(pw.getComputersPerPage());
		 */
		Query query = sf.getCurrentSession().createQuery(sb.toString())
				.setFirstResult(pw.getOffset())
				.setMaxResults(pw.getComputersPerPage());
		computerList = query.list();

		return computerList;
	}
}