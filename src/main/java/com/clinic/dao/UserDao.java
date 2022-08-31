package com.clinic.dao;

import java.util.List;

import com.clinic.entity.Child;
import com.clinic.entity.User;

public interface UserDao {
	
	User getUserByUsername(String username) throws Exception;
	
	User getUserByFullname(String fullname) throws Exception;
	
	User getUserByID (int id) throws Exception;

	List < Child > getChildByUserID (int id) throws Exception;

	Child getChildByID (int id) throws Exception;
	
	boolean updateLastActivity(User user) throws Exception;

}
