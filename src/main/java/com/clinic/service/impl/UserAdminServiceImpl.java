package com.clinic.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.dao.UserAdminDao;
import com.clinic.entity.UserAdmin;
import com.clinic.service.UserAdminService;
import com.clinic.util.Security;

@Service
public class UserAdminServiceImpl implements UserAdminService {
	
	@Autowired
	UserAdminDao userAdminDao;

	@Override
	public boolean updateLastActivity(UserAdmin user) throws Exception {
		user.setLastActivity(new Date());
		user.setUpdatedDtm(new Date());
		user.setUpdatedBy("SYSTEM");
		return userAdminDao.updateLastActivity(user);
	}

	@Override
	public boolean updateSessionId(UserAdmin user) throws Exception {
		return userAdminDao.updateSessionId(user);
	}

	@Override
	public boolean isValidSession(String sessionId, String username) throws Exception {
		UserAdmin userAdmin = userAdminDao.getAdminByUsername(username);
		Date lastActivity = userAdmin.getLastActivity();
		boolean isIdle = false;
		if(lastActivity == null){
			isIdle = true;  //first retry
		}else{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, (10 * -1));
			if(cal.getTime().after(lastActivity)){
				isIdle = true;
			}
		}
		if(sessionId == null){ //login request
			if(userAdmin.getSessionId() == null || "".equalsIgnoreCase(userAdmin.getSessionId())){
				return true; //User already logout
			}else{
				return isIdle;
			}
		} else {   //Transactional request
			if(userAdmin.getSessionId() == null || "".equalsIgnoreCase(userAdmin.getSessionId())){
					return false; //User already logout
			}else{
				if(isIdle){
					return false; //User already idle, force to login
				}else if(sessionId.equalsIgnoreCase(userAdmin.getSessionId())){ //user still active in last 15 minutes
					return true; //valid sessionId
				}else{
					return false; //invalid sessionId
				}
			}
		}
	}

	@Override
	public UserAdmin isValidUser(String username, String password) throws Exception {
		UserAdmin user = userAdminDao.getAdminByUsername(username);
		if (user != null) {
			String decryptedPassword = Security.decrypt(user.getPassword());
			if (decryptedPassword.equals(password)) {
				return user;
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public UserAdmin getAdminByUsername(String username) throws Exception {
		return userAdminDao.getAdminByUsername(username);
	}

}
