package com.excilys.computerdatabase.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.PageWrapper;

@Service
public class ComputerServiceImpl implements ComputerService {

	private static Logger logger = LoggerFactory
			.getLogger(ComputerServiceImpl.class);

	@Autowired
	ComputerDAO myComputerDAO;
	@Autowired
	LogDAO myLogDAO;

	@Override
	@Transactional(readOnly = false)
	public void create(Computer c) {

		logger.debug("computer creation -> started");
		myComputerDAO.create(c);

		Log log = Log.builder().type("Info")
				.description("Creating computer. Name = " + c.getId()).build();
		myLogDAO.create(log);

		logger.debug("computer creation -> ended");
	}

	@Override
	@Transactional(readOnly = false)
	public Computer retrieveById(Long id) {
		Computer computer = null;

		logger.debug("computer retrievement by id-> started");
		computer = myComputerDAO.retrieveById(id);
		Log log = Log.builder().type("Info")
				.description("Looking for computer n° " + computer.getId())
				.build();
		myLogDAO.create(log);

		logger.debug("computerlist retrievement by id-> ended");
		return computer;
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Computer c) {

		logger.debug("computer updating -> started");

		myComputerDAO.update(c);
		Log log = Log.builder().type("Info")
				.description("Updating computer n° ").build();
		myLogDAO.create(log);
		logger.debug("computer updating -> endeded");
	}

	@Override
	@Transactional(readOnly = false)
	public List<Computer> retrieveList(PageWrapper pw) {
		List<Computer> computerList = null;

		logger.debug("computerlist retrievement -> started");
		computerList = myComputerDAO.retrieveAll(pw);
		Log log = Log.builder().type("Info")
				.description("Looking for the whole computer list").build();
		myLogDAO.create(log);
		logger.debug("computerlist retrievement -> ended");
		return computerList;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {

		logger.debug("computer deleting -> started");
		myComputerDAO.delete(id);
		Log log = Log.builder().type("Info")
				.description("Deleting computer n° ").build();
		myLogDAO.create(log);
		logger.debug("computer deleting -> endeded");
	}

}
