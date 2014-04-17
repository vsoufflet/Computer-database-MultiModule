package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.mapper.ComputerRowMapper;
import com.excilys.computerdatabase.wrapper.PageWrapper;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAOImpl implements ComputerDAO {

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	@Qualifier(value = "DataSource")
	private BoneCPDataSource ds;

	@Autowired
	private JdbcTemplate jt;

	public void create(Computer c) {

		/*
		 * Connection conn = DataSourceUtils.getConnection(ds);
		 * PreparedStatement ps = null; StringBuilder query = new
		 * StringBuilder("INSERT INTO computer (name"); int numberOfParam = 1;
		 * 
		 * if (c.getIntroduced() != null) { query.append(", introduced"); } if
		 * (c.getDiscontinued() != null) { query.append(", discontinued"); } if
		 * (c.getCompany() != null) { query.append(", company_id"); }
		 * query.append(") VALUES(?");
		 * 
		 * if (c.getIntroduced() != null) { query.append(",?"); } if
		 * (c.getDiscontinued() != null) { query.append(",?"); } if
		 * (c.getCompany() != null) { query.append(",?"); } query.append(")");
		 * ps = conn.prepareStatement(query.toString());
		 * 
		 * ps.setString(numberOfParam, c.getName());
		 * 
		 * if (c.getIntroduced() != null) { numberOfParam++;
		 * ps.setDate(numberOfParam, new Date(c.getIntroduced().toDate()
		 * .getTime())); } if (c.getDiscontinued() != null) { numberOfParam++;
		 * ps.setDate(numberOfParam, new Date(c.getDiscontinued().toDate()
		 * .getTime())); } if (c.getCompany() != null) { numberOfParam++;
		 * ps.setLong(numberOfParam, c.getCompany().getId()); }
		 * 
		 * ps.executeUpdate();
		 * 
		 * ps.close();
		 */
		jt = new JdbcTemplate(ds);
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
		jt.update(query, c.getName(), c.getIntroduced(), c.getDiscontinued(), c
				.getCompany().getId());
	}

	public Computer retrieveById(Long id) {

		jt = new JdbcTemplate(ds);
		String query = "SELECT * FROM computer WHERE id=?";

		Computer computer = jt.queryForObject(query, new Object[] { id },
				new ComputerRowMapper());

		if (computer.getCompany() != null) {
			Company company = companyDAO.retrieveById(computer.getCompany()
					.getId());
			computer.setCompany(company);
		}
		return computer;
	}

	public void update(Computer c) {

		/*
		 * StringBuilder query = new StringBuilder("UPDATE computer SET (name");
		 * int numberOfParam = 1;
		 * 
		 * if (c.getIntroduced() != null) { query.append(", introduced"); } if
		 * (c.getDiscontinued() != null) { query.append(", discontinued"); } if
		 * (c.getCompany() != null) { query.append(", company_id"); }
		 * 
		 * query.append(") VALUES(?");
		 * 
		 * if (c.getIntroduced() != null) { query.append(",?"); } if
		 * (c.getDiscontinued() != null) { query.append(",?"); } if
		 * (c.getCompany() != null) { query.append(",?"); }
		 * 
		 * query.append(") WHERE id=?"); query.toString();
		 * 
		 * if (c.getIntroduced() != null) { numberOfParam++;
		 * ps.setDate(numberOfParam, new Date(c.getIntroduced().toDate()
		 * .getTime())); } else { numberOfParam++; ps.setDate(numberOfParam,
		 * null); } if (c.getDiscontinued() != null) { numberOfParam++;
		 * ps.setDate(numberOfParam, new Date(c.getDiscontinued().toDate()
		 * .getTime())); } else { numberOfParam++; ps.setDate(numberOfParam,
		 * null); } if (c.getCompany() != null) { numberOfParam++;
		 * ps.setLong(numberOfParam, c.getCompany().getId()); } numberOfParam++;
		 * ps.setLong(numberOfParam, c.getId());
		 * 
		 * ps.executeUpdate();
		 * 
		 * ps.close();
		 */
		jt = new JdbcTemplate(ds);
		String query = "UPDATE computer SET (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
		jt.update(query, c.getName(), c.getIntroduced(), c.getDiscontinued(), c
				.getCompany().getId());
	}

	public void delete(Computer c) {

		jt = new JdbcTemplate(ds);
		String query = "DELETE FROM computer WHERE id=?";
		jt.update(query, c.getId());
	}

	public List<Computer> retrieveAll(PageWrapper pw) {

		jt = new JdbcTemplate(ds);
		List<Computer> computerList = new ArrayList<Computer>();
		// String numberOfLines = "SELECT FOUND_ROWS()";

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SQL_CALC_FOUND_ROWS * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id ");
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
		sb.append("LIMIT ").append(pw.getOffset() + ",")
				.append(pw.getComputersPerPage());
		String query = sb.toString();
		computerList = jt.query(query, new ComputerRowMapper());

		return computerList;
	}
	/*
	 * public List<Computer> retrieveAllByCompany(PageWrapper pw) {
	 * 
	 * List<Computer> computerList = new ArrayList<Computer>(); // String
	 * numberOfLines = "SELECT FOUND_ROWS()";
	 * 
	 * String query =
	 * "SELECT SQL_CALC_FOUND_ROWS * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? ORDER BY ? LIMIT ?, ?"
	 * ; computerList = jt.query(query, new Object[] { "'%" + pw.getSearch() +
	 * "%'", pw.getOrderBy(), pw.getOffset(), pw.getComputersPerPage() }, new
	 * ComputerRowMapper()); return computerList; }
	 */
}