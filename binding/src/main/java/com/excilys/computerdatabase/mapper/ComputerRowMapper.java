package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {

		Computer computer = new Computer();
		computer.setId(rs.getLong(1));
		computer.setName(rs.getString(2));

		if (rs.getDate(3) != null) {
			computer.setIntroduced(new DateTime(rs.getDate(3)));
		}
		if (rs.getDate(4) != null) {
			computer.setDiscontinued(new DateTime(rs.getDate(4)));
		}
		if (rs.getLong(5) != 0) {
			Company company = new Company();
			company.setId(rs.getLong(5));
			computer.setCompany(company);
		}
		return computer;
	}

}
