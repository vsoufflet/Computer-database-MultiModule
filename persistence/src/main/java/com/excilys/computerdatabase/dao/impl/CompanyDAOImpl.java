package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.mapper.CompanyRowMapper;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	@Qualifier(value = "DataSource")
	private BoneCPDataSource ds;

	@Autowired
	private JdbcTemplate jt;

	public Company retrieveById(Long id) {

		jt = new JdbcTemplate(ds);
		String query = "SELECT * FROM company WHERE id=?";
		Company company = jt.queryForObject(query, new Object[] { id },
				new CompanyRowMapper());
		return company;
	}

	public List<Company> retrieveList() {

		jt = new JdbcTemplate(ds);
		String query = "SELECT id,name FROM company";
		List<Company> companyList = new ArrayList<Company>();
		companyList = jt.query(query, new CompanyRowMapper());

		return companyList;
	}
}
