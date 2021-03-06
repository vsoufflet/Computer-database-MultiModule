package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.wrapper.PageWrapper;

public interface ComputerDAO {

	public void create(Computer c);

	public Computer retrieveById(Long id);

	public void update(Computer c);

	public void delete(Long id);

	public List<Computer> retrieveAll(PageWrapper pw);

}
