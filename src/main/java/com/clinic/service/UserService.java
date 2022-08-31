package com.clinic.service;

import java.util.List;

import com.clinic.entity.Child;
import com.clinic.entity.User;

public interface UserService {

	User getUserByID (int id) throws Exception;

	User getUserByUsername (String username) throws Exception;
	
	Child getChildByID (int id) throws Exception;

	List < Child > getChildByUserID (int id) throws Exception;
	
	boolean checkValidUser (String username, String password) throws Exception;

	boolean updateLastActivity(User user) throws Exception;
	
}
