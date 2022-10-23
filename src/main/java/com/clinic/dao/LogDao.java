package com.clinic.dao;

import java.util.List;

import com.clinic.entity.Log;

public interface LogDao {

	boolean saveLog (Log log);
	
	List < Log > getLogs (String username);
	
}
