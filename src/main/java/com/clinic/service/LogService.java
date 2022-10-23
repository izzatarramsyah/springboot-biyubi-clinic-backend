package com.clinic.service;

import java.util.List;
import com.clinic.entity.Log;

public interface LogService {
	
	boolean saveLog (Log log);
	
	List < Log > getLogs (String username);

}
