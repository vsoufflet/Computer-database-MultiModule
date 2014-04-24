package com.excilys.computerdatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.domain.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
