package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.domain.Company;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	private SessionFactory sf;

	public Company retrieveById(Long id) {

		Company company = (Company) sf.getCurrentSession().get(Company.class,
				id);

		return company;
	}

	public List<Company> retrieveList() {

		Query query = sf.getCurrentSession().createQuery(
				"SELECT company FROM Company AS company");
		List<Company> companyList = new ArrayList<Company>();
		companyList = query.list();

		return companyList;
	}
}
