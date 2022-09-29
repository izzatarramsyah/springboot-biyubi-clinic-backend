package com.clinic.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.AuditTrailDao;
import com.clinic.entity.AuditTrail;
import com.clinic.service.AuditTrailService;

@Service
public class AuditTrailServiceImpl implements AuditTrailService {

	@Autowired
	AuditTrailDao auditTrailDao;
	
	@Override
	public boolean saveAuditTrail(AuditTrail auditTrail) {
		return auditTrailDao.saveAuditTrail(auditTrail);
	}

	@Override
	public List< AuditTrail > getAuditTrail(String username, Date startDate, Date endDate) {
		return auditTrailDao.getAuditTrail(username, startDate, endDate);
	}

}
