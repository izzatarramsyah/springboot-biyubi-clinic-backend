package com.clinic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.api.object.ChildData;
import com.clinic.api.object.UserData;
import com.clinic.constant.Constant;
import com.clinic.controller.UserController;
import com.clinic.dao.MasterDao;
import com.clinic.dao.UserDao;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.Child;
import com.clinic.entity.GrowthDtl;
import com.clinic.entity.User;
import com.clinic.service.CheckUpService;
import com.clinic.service.MasterService;
import com.clinic.service.UserService;
import com.clinic.util.Security;
import com.clinic.util.Util;

@Service
public class UserServiceImpl implements UserService{
		
	@Autowired
	UserDao userDao;
	
	@Autowired
	MasterService masterService;
	
	@Autowired
	CheckUpService checkUpService;
	
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
	public Child getChildByFullname(String fullname) throws Exception {
		return userDao.getChildByFullname(fullname);
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

	@Override
	public ChildData getChildDetails(User user, Child child) throws Exception {
		 
		int batch = 0;
		double weight = 0; String weightCategory = null; String weightNotes = null;
		List < Double > seriesWeight = new ArrayList < Double >();
		double length = 0; String lengthCategory = null; String lengthNotes = null;
		List < Double > seriesLength = new ArrayList < Double >();
		double headDiameter = 0; String headDiameterCategory = null; String headDiameterNotes = null;
		List < Double > seriesHeadDiameter = new ArrayList < Double >();
		
		for (CheckUpMaster lst : masterService.getListMstCheckUp()) {
			CheckUpRecord checkUp = checkUpService.getCheckUpRecord(child.getUserId(), child.getId(), lst.getCode());
			if (checkUp != null) {
				GrowthDtl growthDtl = checkUpService.getGrowthDtl(checkUp.getMstCode(), checkUp.getId());
				batch = lst.getBatch();
				seriesWeight.add((double) growthDtl.getWeight());
				seriesLength.add((double) growthDtl.getLength());
				seriesHeadDiameter.add((double) growthDtl.getHeadDiameter());
			}
		}	
		
		if (seriesWeight.size() > 0) {
			int index = seriesWeight.size() - 1;
			weight = seriesWeight.get(index);
			weightCategory = masterService.category(Constant.WEIGHT, batch, seriesWeight.get(index));
			weightNotes = Util.getWeightNotes(weightCategory);
		}
		
		
		if (seriesLength.size() > 0) {
			int index = seriesLength.size() - 1;
			length = seriesLength.get(index);
			lengthCategory = masterService.category(Constant.LENGTH, batch, seriesLength.get(index));
			lengthNotes = Util.getLengthNotes(lengthCategory);
		}

		if (seriesHeadDiameter.size() > 0) {
			int index = seriesHeadDiameter.size() - 1;
			headDiameter = seriesHeadDiameter.get(index);
			headDiameterCategory = masterService.category(Constant.HEAD_CIRCUMFERENCE, batch, seriesHeadDiameter.get(index));
			headDiameterNotes = Util.getHeadDiameterNotes(headDiameterCategory);
		}
	
		return  new ChildData().setAttribute(child.getId(), child.getFullname(), child.getBirthDate(), child.getGender(),
				weight, weightCategory, weightNotes, length, lengthCategory, lengthNotes, headDiameter, headDiameterCategory, headDiameterNotes, 
				seriesWeight, seriesLength, seriesHeadDiameter);
	}

	@Override
	public boolean changePassword(User user, String newPassword) throws Exception {
		String encPassword = Security.encrypt( newPassword );
		return userDao.changePassword(user, encPassword);
	}

}
