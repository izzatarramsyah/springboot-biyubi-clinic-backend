package com.clinic.dao;

import com.clinic.entity.UserAdmin;

public interface UserAdminDao {

	UserAdmin getAdminByUsername(String username) throws Exception;

	boolean updateLastActivity(UserAdmin user) throws Exception;
	
	boolean updateSessionId(UserAdmin user) throws Exception;
	
}
