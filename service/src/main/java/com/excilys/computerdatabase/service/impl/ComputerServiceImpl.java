package com.excilys.computerdatabase.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.ComputerRepository;
import com.excilys.computerdatabase.dao.LogRepository;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ListWrapper;
import com.excilys.computerdatabase.wrapper.PageWrapper;

@Service
public class ComputerServiceImpl implements ComputerService {

	private static Logger logger = LoggerFactory
			.getLogger(ComputerServiceImpl.class);

	@Autowired
	ComputerRepository myComputerDAO;
	@Autowired
	LogRepository myLogDAO;

	@Override
	@Transactional(readOnly = false)
	public void create(Computer c) {

		logger.debug("computer creation -> started");
		myComputerDAO.save(c);

		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Creating computer. Name = " + c.getId()).build();
		myLogDAO.save(log);

		logger.debug("computer creation -> ended");
	}

	@Override
	@Transactional(readOnly = false)
	public Computer retrieveById(Long id) {
		Computer computer = null;

		logger.debug("computer retrievement by id-> started");
		computer = myComputerDAO.findOne(id);
		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Looking for computer n° " + computer.getId())
				.build();
		myLogDAO.save(log);

		logger.debug("computerlist retrievement by id-> ended");
		return computer;
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Computer c) {

		logger.debug("computer updating -> started");

		myComputerDAO.save(c);
		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Updating computer n° ").build();
		myLogDAO.save(log);
		logger.debug("computer updating -> endeded");
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Computer c) {

		logger.debug("computer deleting -> started");
		myComputerDAO.delete(c);
		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Deleting computer n° ").build();
		myLogDAO.save(log);
		logger.debug("computer deleting -> endeded");
	}

	@Override
	@Transactional(readOnly = false)
	public ListWrapper retrieveList(PageWrapper pw) {
		List<Computer> computerList = null;

		ListWrapper wrapper = new ListWrapper();

		logger.debug("computerlist retrievement -> started");
		PageRequest page = new PageRequest(pw.getCurrentPage() - 1,
				pw.getComputersPerPage(), Direction.ASC, pw.getOrderBy());
		int count = 0;

		if ("computer".equalsIgnoreCase(pw.getSearchBy())
				&& !pw.getSearch().equalsIgnoreCase("")) {
			computerList = myComputerDAO.findByNameContaining(pw.getSearch(),
					page);
			count = myComputerDAO.countByNameContaining(pw.getSearch());
		} else if ("company".equalsIgnoreCase(pw.getSearchBy())
				&& !pw.getSearch().equalsIgnoreCase("")) {
			computerList = myComputerDAO.findByCompanyNameContaining(
					pw.getSearch(), page);
			count = myComputerDAO.countByCompanyNameContaining(pw.getSearch());
		} else {
			computerList = myComputerDAO.findAll(page).getContent();
			count = (int) myComputerDAO.count();
		}
		Log log = Log.builder().date(new DateTime()).type("Info")
				.description("Looking for the whole computer list").build();
		myLogDAO.save(log);

		wrapper.setComputerList(computerList);
		wrapper.setNumberofComputers(count);

		logger.debug("computerlist retrievement -> ended");
		return wrapper;
	}
}
