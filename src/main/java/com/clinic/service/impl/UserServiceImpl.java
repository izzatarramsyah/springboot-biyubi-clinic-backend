package com.clinic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.UserDao;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.service.UserService;
import com.clinic.util.Security;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	@Override
	public User getUserByID (int id) throws Exception {
		User user = userDao.getUserByID(id);
//		if (user != null) {
//			List < Child > child = userDao.getChildByUserID(user.getId());
//			user.setChild(child);
//			return user;
//		}
		return user;
	}
	
	@Override
	public User getUserByUsername (String username) throws Exception {
		User user = userDao.getUserByUsername(username);
//		if (user != null) {
//			List < Child > child = userDao.getChildByUserID(user.getId());
//			user.setChild(child);
//			return user;
//		}
		return user;
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

}
