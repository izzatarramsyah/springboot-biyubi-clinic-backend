package com.clinic.service;

import java.util.Date;
import java.util.List;

import com.clinic.entity.AuditTrail;

public interface AuditTrailService {
	
	boolean saveAuditTrail (AuditTrail auditTrail);
	
	List < AuditTrail > getAuditTrail (String username, Date startDate, Date endDate);

}
