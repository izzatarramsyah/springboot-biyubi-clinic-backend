package com.clinic.service;

import java.util.List;

import com.clinic.api.object.ChildData;
import com.clinic.api.object.InfoUserID;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.UserAdmin;

public interface UserService {

	List < User > getListUser() throws Exception;

	List < InfoUserID > getListIDUser() throws Exception;

	User getUserByID (int id) throws Exception;

	User getUserByUsername (String username) throws Exception;
	
	User getUserByFullname(String fullname) throws Exception;
	
	Child getChildByID (int id) throws Exception;

	Child getChildByFullname (String fullname) throws Exception;

	List < Child > getChildByUserID (int id) throws Exception;
	
	boolean insertUser (User User) throws Exception;
	
	int insertChild (Child child) throws Exception;

	boolean updateUser (User user) throws Exception;
	
	boolean changeStatusUser (User user) throws Exception;
	
	boolean updateChild (Child child) throws Exception;
	
	boolean checkValidUser (String username, String password) throws Exception;

	boolean updateLastActivity(User user) throws Exception;
	
	ChildData getChildDetails(User user, Child child) throws Exception;
	
	boolean changePassword (User user, String newPassword) throws Exception;

}
