package com.clinic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.api.object.CheckUpRequest;
import com.clinic.api.object.ChildData;
import com.clinic.api.object.ChildRegistration;
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.InfoChild;
import com.clinic.api.object.InfoRq;
import com.clinic.api.object.InfoUser;
import com.clinic.api.object.UserData;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.Constant;
import com.clinic.constant.StatusCode;
import com.clinic.entity.AuditTrail;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.Child;
import com.clinic.entity.GrowthDtl;
import com.clinic.entity.User;
import com.clinic.entity.UserAdmin;
import com.clinic.service.AuditTrailService;
import com.clinic.service.CheckUpService;
import com.clinic.service.MailService;
import com.clinic.service.MasterService;
import com.clinic.service.UserAdminService;
import com.clinic.service.UserService;
import com.clinic.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserProcessController extends BaseController {
	
	private static final Logger LOG = LogManager.getLogger(UserProcessController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	CheckUpService checkUpService;

	@Autowired
	MasterService masterService;
	
	@Autowired
	UserAdminService userAdminService;
	
//	@Autowired
//	AuditTrailService auditTrailService;
	
	@Autowired
	MailService mailService;
	
	@RequestMapping(value = "/getUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getUser(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObj = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		try {
			LOG.info("GET USER");
			APIRequest < InfoRq > req = getInfoRq(input);
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB ) || req.getHeader().getChannel().equals( Constant.CHANNEL_MOBILE )) {
				UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
				if (userAdmin == null) {
					statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
				} else { 
					switch ( req.getHeader().getCommand() ) { 
					  case Constant.INFO_USER:
						  User user = userService.getUserByID(req.getPayload().getUserId());
						  if (user == null) {
							  statusTrx = StatusCode.USER_NOT_FOUND;
						  } else {
							  List < ChildData > listChildData = new ArrayList < ChildData >();
							  UserData userData = new UserData().setAttribute(user);
							  if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
								  Child child = userService.getChildByID( req.getPayload().getChildId() ); 
								  if ( child == null ) {
									  statusTrx = StatusCode.CHILD_NOT_FOUND;
								  } else { 
									  ChildData childData = userService.getChildDetails(user, child);
									  listChildData.add(childData);
								      userData.setChildData(listChildData);
								      responseObj.put("object", userData);
								  }
							  } else if (req.getHeader().getChannel().equals( Constant.CHANNEL_MOBILE )){
								  for (Child child : userService.getChildByUserID( user.getId() )) {
									  ChildData childData = userService.getChildDetails(user, child);
									  listChildData.add(childData);
								  }
							      userData.setChildData(listChildData);
							      responseObj.put("object", userData);
							  }
						  }
						  break;
					  case Constant.INFO_ALL_USER:
						  List < User > listUser = userService.getUser();
						  for (User usr : listUser) {
							  List < Child > child = userService.getChildByUserID(usr.getId());
							  usr.setChild(child);
						  }
						  responseObj.put("object", listUser);
						  break;
					  case Constant.INFO_SIMPLE_USER:
						  List < InfoUser > infoUser = new ArrayList < InfoUser > ();
						  for (User usr : userService.getUser()) {
							  InfoUser info = new InfoUser();
							  info.setId( usr.getId() );
							  info.setFullname( usr.getFullname() );
							  List < InfoChild > infoChild = new ArrayList < InfoChild > (); 
							  for (Child chd : userService.getChildByUserID(usr.getId()) ) {
								  InfoChild infoo = new InfoChild();
								  infoo.setId( chd.getId() );
								  infoo.setFullname( chd.getFullname() );
								  infoChild.add( infoo );
							  }
							  info.setListChild(infoChild);
							  infoUser.add(info);
						  }
						  responseObj.put("object", infoUser);
						  break;
					  default:
						  break;
					}
					userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
				}
			}
			response.setPayload(responseObj);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}

	@RequestMapping(value = "/userProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> userProcess(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		User user = new User();
		try {
			LOG.info("USER PROCESS");
			APIRequest < User > req = getRequestUser(input);
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
				if (userAdmin == null) {
					statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
				} else { 
					switch ( req.getHeader().getCommand() ) { 
					  case Constant.USER_REGISTARTION:
						  user = userService.getUserByFullname( req.getPayload().getFullname() );
						  if ( user != null ) {
							  statusTrx = StatusCode.ALREDY_REGISTERD;
						  } else {
							  boolean isUserSaved = userService.insertUser( req.getPayload() );
							  if ( isUserSaved == false ) {
								  statusTrx = StatusCode.FAILED_PROCESS;
							  }  else {
								  user = userService.getUserByFullname( req.getPayload().getFullname() );
								  String value2 = Constant.VALUE_INSERT_USER.replaceAll("<fullname>", user.getFullname()).replaceAll("<joinDate>", Util.formatDate(new Date()));
								  //auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_USER_REGISTRATION, req.getHeader().getuName(), value2) );
								  userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
							  }
						  }
						  break;
					  case Constant.USER_UPDATE:
						  user = userService.getUserByID( req.getPayload().getId() );
						  if (user != null) {
							  boolean isUpdateUser = userService.updateUser( req.getPayload() );
							  if (isUpdateUser) {
								  String value2 = Constant.VALUE_UPDATE_USER.replaceAll("<fullname>", user.getFullname()).replaceAll("<email>", req.getPayload().getEmail())
										  .replaceAll("<phone_no>", req.getPayload().getPhone_no()).replaceAll("<address>", req.getPayload().getAddress());
								  //auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_USER_UPDATE, req.getHeader().getuName(), value2 ) );
								  userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
							  } else {
								  statusTrx = StatusCode.FAILED_PROCESS;
							  }
						  } else {
							  statusTrx = StatusCode.DATA_NOT_FOUND;
						  }
						  break;
					  case Constant.USER_CHANGE_STATUS:
						  user = userService.getUserByID( req.getPayload().getId() );
						  if (user != null) {
							  boolean isChanged = userService.changeStatusUser( req.getPayload() );
							  if (isChanged) {
								  String value2 = Constant.VALUE_CHANGE_STATUS_USER.replaceAll("<fullname>", user.getFullname()).replaceAll("<status>", req.getPayload().getStatus());
								  //auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_USER_STATUS_UPDATE, req.getHeader().getuName(), value2 ) );
								  userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
							  } else {
								  statusTrx = StatusCode.FAILED_PROCESS;
							  }
						  } else {
							  statusTrx = StatusCode.DATA_NOT_FOUND;
						  }
					  default:
						  break;
					}
				}
			}
			response.setPayload(responseObject);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}
	
	
	@RequestMapping(value = "/childProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> childProcess(@RequestBody String input) {
	LOG.traceEntry();
	APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
	HashMap< String, Object > responseObject = new HashMap< String, Object > ();
	StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
	Child child = new Child();
	try {
		LOG.info("CHILD PROCESS");
		APIRequest < ChildRegistration > req = getRequestChildRegistration(input);
		if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
			UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			if (userAdmin == null) {
				statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
			} else { 
				switch ( req.getHeader().getCommand() ) { 
				  case Constant.CHILD_REGISTARTION:
					  child.setUserId( req.getPayload().getUserId() );
					  child.setFullname( req.getPayload().getFullname() );
					  child.setBirthDate( req.getPayload().getBirthDate() );
					  child.setGender( req.getPayload().getGender() );
					  child.setNotes( req.getPayload().getNotes() );
					  int id = userService.insertChild( child );
					  if ( id > 0 ) {
						  child = userService.getChildByID(id);
						  CheckUpRequest checkHealth = new CheckUpRequest();
						  checkHealth.setUserId(req.getPayload().getUserId());
						  checkHealth.setChildId(child.getId());
						  checkHealth.setWeight(req.getPayload().getWeight());
						  checkHealth.setLength(req.getPayload().getLength());
						  checkHealth.setHeadDiameter(req.getPayload().getHeadDiameter());
						  checkHealth.setMstCode("ACT_000");
						  checkHealth.setBatch(0);
						  checkHealth.setNotes(req.getPayload().getNotes());
						  checkHealth.setCheckUpDate(new Date());
						  boolean isSaved = checkUpService.addCheckUpRecord(checkHealth);
						  if (isSaved) {
							  String value2 = Constant.VALUE_RECORD_CHECK_UP.replaceAll("<childName>", child.getFullname()).replaceAll("<weight>", String.valueOf(req.getPayload().getWeight()))
						    		  .replaceAll("<length>", String.valueOf(req.getPayload().getLength())).replaceAll("<headDiameter>", String.valueOf(req.getPayload().getHeadDiameter()));
							  //auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_CHILD_REGISTRATION, req.getHeader().getuName(), value2 ) );
						  }
						  userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
					  } else {
						  statusTrx = StatusCode.FAILED_PROCESS;
					  }
					  break;
				  case Constant.CHILD_UPDATE:
					  child = userService.getChildByID(req.getPayload().getId());
					  if ( child == null ) {
						  statusTrx = StatusCode.DATA_NOT_FOUND;
					  } else {
						  child.setId( req.getPayload().getId() );
						  child.setFullname( req.getPayload().getFullname() );
						  child.setBirthDate( req.getPayload().getBirthDate() );
						  child.setGender( req.getPayload().getGender() );
						  boolean isUpdated = userService.updateChild( child );
						  if (isUpdated) {
							  String value2 = Constant.VALUE_UPDATE_CHILD.replaceAll("<childName>", req.getPayload().getFullname()).
									  replaceAll("<gender>", req.getPayload().getGender()).replaceAll("<birthDate>", Util.formatDate(req.getPayload().getBirthDate()));
							  //auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_CHILD_UPDATE, req.getHeader().getuName(), value2 ) );
							  userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
						  } else {
							  statusTrx = StatusCode.FAILED_PROCESS;
						  }
					  } 	
					  break;
				  default:
					  break;
				}
			}
		}
		response.setPayload(responseObject);
	}catch (Exception e){
		e.printStackTrace();
		LOG.error("ERR::[{}]:{}", e.getMessage());
		statusTrx = StatusCode.GENERIC_ERROR;
	}
	response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
	LOG.traceExit();
	return response;
	}

}

