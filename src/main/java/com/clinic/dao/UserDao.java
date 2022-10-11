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
	
	Child getChildByFullname (String fullname) throws Exception;

	boolean insertUser (User user) throws Exception;
	
	int insertChild (Child child) throws Exception;
	
	boolean updateUser (User user) throws Exception;
	
	boolean changeStatusUser (User user) throws Exception;
	
	boolean updateChild (Child child) throws Exception;
	
	boolean updateLastActivity(User user) throws Exception;
	
	List < User > getUser () throws Exception;

	boolean changePassword (User user, String newPassword) throws Exception;

}
