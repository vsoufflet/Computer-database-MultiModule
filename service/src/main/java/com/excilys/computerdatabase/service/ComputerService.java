package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.wrapper.ListWrapper;
import com.excilys.computerdatabase.wrapper.PageWrapper;

public interface ComputerService {

	public void create(Computer c);

	public Computer retrieveById(Long id);

	public void update(Computer c);

	public ListWrapper retrieveList(PageWrapper pw);

	public ListWrapper retrieveList();

	public void delete(Computer c);

}
