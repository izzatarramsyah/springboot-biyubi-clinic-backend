package com.clinic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.ApplicationConfigDao;
import com.clinic.entity.ApplicationConfig;
import com.clinic.service.ApplicationConfigService;

@Service
public class ApplicationConfigServiceImpl implements ApplicationConfigService{

	@Autowired
	ApplicationConfigDao dao;
	
	@Override
	public List<ApplicationConfig> get(String appsName, String serviceName) {
		return dao.get(appsName, serviceName);
	}

}
