package com.excilys.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ListWrapper;

/**
 * Jax-WebService
 * 
 */

@WebService
public class ComputerWebService {

	@Autowired
	ComputerService myComputerService;

	@WebMethod
	public List<Computer> display() {
		ListWrapper wrapper = myComputerService.retrieveList();
		List<Computer> computerList = wrapper.getComputerList();

		return computerList;
	}
}
