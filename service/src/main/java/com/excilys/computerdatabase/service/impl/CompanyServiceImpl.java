package com.excilys.computerdatabase.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.service.CompanyService;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Autowired
	CompanyDAO myCompanyDAO;
	@Autowired
	LogDAO myLogDAO;

	@Override
	@Transactional(readOnly = false)
	public Company retrieveById(Long id) {
		Company company = null;

		logger.debug("company retrievement by id-> started");
		company = myCompanyDAO.retrieveById(id);
		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Looking for company n° " + id).build();
		myLogDAO.create(log);
		logger.debug("company retrievement by id-> ended");
		return company;
	}

	@Override
	@Transactional(readOnly = false)
	public List<Company> retrieveList() {
		List<Company> companyList = null;

		logger.debug("companyList retrievement -> started");
		companyList = myCompanyDAO.retrieveList();
		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Looking for the whole company list").build();
		myLogDAO.create(log);
		logger.debug("companyList retrievement -> ended");
		return companyList;
	}
}
