package com.clinic.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.LogDao;
import com.clinic.entity.Log;
import com.clinic.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogDao dao;
	
	@Override
	public boolean saveLog(Log log) {
		return dao.saveLog(log);
	}

	@Override
	public List< Log > getLogs(String username) {
		return dao.getLogs(username);
	}

}
