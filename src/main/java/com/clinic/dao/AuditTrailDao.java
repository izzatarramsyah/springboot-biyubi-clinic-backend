package com.clinic.dao;

import java.util.Date;
import java.util.List;

import com.clinic.entity.AuditTrail;

public interface AuditTrailDao {

	boolean saveAuditTrail (AuditTrail auditTrail);
	
	List < AuditTrail > getAuditTrail (String username, Date startDate, Date endDate);
	
}
