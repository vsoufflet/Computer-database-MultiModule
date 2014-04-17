package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyDAO {

	public Company retrieveById(Long id);

	public List<Company> retrieveList();

}
