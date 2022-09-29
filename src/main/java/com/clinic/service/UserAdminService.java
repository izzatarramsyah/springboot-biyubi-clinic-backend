package com.clinic.service;

import com.clinic.entity.UserAdmin;

public interface UserAdminService {

	UserAdmin getAdminByUsername (String username) throws Exception;
	
	UserAdmin isValidUser(String username, String password) throws Exception;
	
	boolean isValidSession(String sessionId, String username) throws Exception;
	
	boolean updateLastActivity(UserAdmin user) throws Exception;
	
	boolean updateSessionId(UserAdmin user) throws Exception;
	
}
