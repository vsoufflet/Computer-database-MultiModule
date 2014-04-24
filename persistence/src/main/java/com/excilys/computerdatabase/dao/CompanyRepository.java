package com.excilys.computerdatabase.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	public Company findById(Long id);

	public List<Company> findAll();

}
