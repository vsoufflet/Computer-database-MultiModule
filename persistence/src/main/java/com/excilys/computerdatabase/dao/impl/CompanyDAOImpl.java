package com.excilys.computerdatabase.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.QCompany;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	private SessionFactory sf;

	public Company retrieveById(Long id) {

		QCompany qcompany = QCompany.company;
		HibernateQuery query = new HibernateQuery(sf.getCurrentSession());
		Company company = query.from(qcompany).where(qcompany.id.eq(id))
				.uniqueResult(qcompany);
		return company;
	}

	public List<Company> retrieveList() {

		List<Company> companyList = new ArrayList<Company>();
		QCompany company = QCompany.company;
		HibernateQuery query = new HibernateQuery(sf.getCurrentSession());

		companyList = query.from(company).list(company);

		return companyList;
	}
}
