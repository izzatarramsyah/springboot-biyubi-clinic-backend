package com.clinic.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.controller.UserController;
import com.clinic.dao.UserDao;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.service.UserService;
import com.clinic.util.Security;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
	
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
			LOG.info(decryptedPassword + " " + password);
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

}
