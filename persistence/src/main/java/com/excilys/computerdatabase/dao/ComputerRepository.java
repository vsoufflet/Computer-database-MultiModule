package com.excilys.computerdatabase.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.domain.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> {

	int countByNameContaining(String name);

	List<Computer> findByNameContaining(String name, Pageable page);

	int countByCompanyNameContaining(String name);

	List<Computer> findByCompanyNameContaining(String name, Pageable page);

}
