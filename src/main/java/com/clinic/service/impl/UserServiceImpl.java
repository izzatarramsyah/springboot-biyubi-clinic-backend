package com.clinic.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.constant.Constant;
import com.clinic.controller.UserController;
import com.clinic.dao.UserDao;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.service.UserService;
import com.clinic.util.Security;
import com.clinic.util.Util;

@Service
public class UserServiceImpl implements UserService{
		
	@Autowired
	UserDao userDao;
	
	@Override
	public User getUserByID (int id) throws Exception {
		return userDao.getUserByID(id);
	}
	
	@Override
	public User getUserByUsername (String username) throws Exception {
		return userDao.getUserByUsername(username);
	}

	@Override
	public List < Child > getChildByUserID(int id) throws Exception {
		return userDao.getChildByUserID(id);
	}
	
	@Override
	public boolean checkValidUser (String username, String password) throws Exception {
		User user = userDao.getUserByUsername(username);
		if (user != null) {
			String decryptedPassword = Security.decrypt(user.getPassword());
			if (decryptedPassword.equals(password)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	@Override
	public boolean updateLastActivity (User user) throws Exception {
		return userDao.updateLastActivity(user);
	}

	@Override
	public Child getChildByID(int id) throws Exception {
		return userDao.getChildByID(id);
	}

	@Override
	public List < User > getUser() throws Exception {
		return userDao.getUser();
	}

	@Override
	public User getUserByFullname(String fullname) throws Exception {
		return userDao.getUserByFullname(fullname);
	}

	@Override
	public boolean insertUser(User user) throws Exception {
		user.setUsername(user.getEmail().substring(0, user.getEmail().indexOf("@")));
		user.setPassword(Security.encrypt(Constant.DEFAULT_PASSWORD));
		user.setPhone_no(Util.formatPhoneNo(user.getPhone_no()));
		user.setCreatedDtm(new Date());
		user.setCreatedBy("SYSTEM");
		user.setStatus( "ACTIVE" );
		user.setJoinDate(new Date());
		return userDao.insertUser(user);
	}

	@Override
	public int insertChild(Child child) throws Exception {
		child.setCreatedDtm(new Date());
		child.setCreatedBy("SYSTEM");
		return userDao.insertChild(child);
	}

	@Override
	public boolean updateUser(User user) throws Exception {
		user.setUpdatedDtm( new Date() );
		user.setUpdatedBy( "SYSTEM" );
		return userDao.updateUser(user);
	}

	@Override
	public boolean changeStatusUser(User user) throws Exception {
		user.setUpdatedDtm( new Date() );
		user.setUpdatedBy( "SYSTEM" );
		return userDao.changeStatusUser(user);
	}

	@Override
	public boolean updateChild(Child child) throws Exception {
		child.setUpdatedDtm( new Date() );
		child.setUpdatedBy( "SYSTEM" );
		return userDao.updateChild(child);
	}

}
