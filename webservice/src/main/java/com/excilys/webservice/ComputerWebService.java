package com.excilys.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ListWrapper;

/**
 * Jax-WebService
 * 
 */
@Component
@Path("/webservice")
public class ComputerWebService {

	@Autowired
	ComputerService myComputerService;

	@GET
	@Produces("application/xml")
	public List<Computer> display() {
		ListWrapper wrapper = myComputerService.retrieveList();
		List<Computer> computerList = wrapper.getComputerList();

		return computerList;
	}
}
