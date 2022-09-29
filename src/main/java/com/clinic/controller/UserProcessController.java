package com.clinic.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.clinic.report.ExportPDF;
import com.clinic.service.AuditTrailService;
import com.clinic.service.CheckUpService;
import com.clinic.service.MailService;
import com.clinic.service.MasterService;
import com.clinic.service.UserAdminService;
import com.clinic.service.UserService;
import com.clinic.util.MailHelper;
import com.clinic.util.Util;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserProcessController extends BaseController {
	
	private static final Logger LOG = LogManager.getLogger(UserProcessController.class);
	
	@Value("${mail.titile.registerd.account}")
	private String mailTitleRegisterdAccount;
	
	@Value("${mail.titile.registerd.child}")
	private String mailTitleRegisterdChild;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CheckUpService checkUpService;

	@Autowired
	MasterService masterService;
	
	@Autowired
	UserAdminService userAdminService;
	
	@Autowired
	AuditTrailService auditTrailService;
	
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
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				
				UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
				if (userAdmin == null) {
					
					statusTrx = StatusCode.USER_NOT_FOUND;
					
				} else { 
					
					switch ( req.getHeader().getCommand() ) { 
					
					  case "info-user":
						  
						  LOG.info("COMMAND : INFO-USER");
						  
						  User user = userService.getUserByID(req.getPayload().getUserId());
						  if (user == null) {
							  
							  statusTrx = StatusCode.DATA_NOT_FOUND;
							  
						  } else {
							  
							  UserData userData = new UserData().setAttribute(user);
							  List < ChildData > listChildData = new ArrayList < ChildData >();
							  
							  for (Child child : userService.getChildByUserID(req.getPayload().getUserId()) ) {
								  
								  ChildData childData = new ChildData().setAttribute(child);
								  
								  List < Double > seriesWeight = new ArrayList < Double >();
								  List < Integer > seriesLength = new ArrayList < Integer >();
								  List < Double > seriesHeadDiameter = new ArrayList < Double >();

								  int batch = 0;
								  for (CheckUpMaster lst : masterService.getListMstCheckUp()) {
									  CheckUpRecord checkUp = checkUpService.getCheckUpRecord(child.getUserId(), child.getId(), lst.getCode());
									  if (checkUp != null) {
										  GrowthDtl growthDtl = checkUpService.getGrowthDtl(checkUp.getMstCode(), checkUp.getId());
										  batch = lst.getBatch();
										  seriesWeight.add((double) growthDtl.getWeight());
										  seriesLength.add(growthDtl.getLength());
										  seriesHeadDiameter.add((double) growthDtl.getHeadDiameter());
									  }
								  }
								  
								  childData.setSeriesWeight(seriesWeight);
								  childData.setSeriesLength(seriesLength);
								  childData.setSeriesHeadDiameter(seriesHeadDiameter);
								  
								  if (seriesWeight.size() > 0) {
									  int index = seriesWeight.size() - 1;
									  String weightCategory = masterService.category("WEIGHT", batch, seriesWeight.get(index));
									  childData.setWeight( seriesWeight.get(index) );
									  childData.setWeightCategory( weightCategory );
									  childData.setWeightNotes( Util.getWeightNotes(weightCategory) );
								  }
								  
								  if (seriesLength.size() > 0) {
									  int index = seriesLength.size() - 1;
									  String lengthCategory = masterService.category("LENGTH", batch, seriesLength.get(index));
									  childData.setLength( seriesLength.get(index) );
									  childData.setLengthCategory( lengthCategory );
									  childData.setLengthNotes( Util.getLengthNotes(lengthCategory) );
								  }
								  
								  if (seriesHeadDiameter.size() > 0) {
									  int index = seriesHeadDiameter.size() - 1;
									  String headDiameterCategory = masterService.category("HEAD CIRCUMFERENCE", batch, seriesHeadDiameter.get(index));
									  childData.setHeadDiameter( seriesHeadDiameter.get(index) );
									  childData.setHeadDiameterCategory( headDiameterCategory );
									  childData.setHeadDiameterNotes( Util.getHeadDiameterNotes(headDiameterCategory) );
								  }
								  
								  listChildData.add(childData);
								  
							  }
								
							  userData.setChildData(listChildData);
							  responseObj.put("object", userData);
							  
						  }
						  
						  break;
					  case "info-all-user":
						  
						  LOG.info("COMMAND : INFO-ALL-USER");
						  
						  List < User > listUser = userService.getUser();
						  for (User usr : listUser) {
							  List < Child > child = userService.getChildByUserID(usr.getId());
							  usr.setChild(child);
						  }
						  responseObj.put("object", listUser);
						  
						  break;
					  case "info-all-simple-user":
						  
						  LOG.info("COMMAND : INFO-ALL-SIMPLE-USER");
						  
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
					
					statusTrx = StatusCode.USER_NOT_FOUND;
					
				} else { 
					
					switch ( req.getHeader().getCommand() ) { 
					
					  case "save":
						  
						  LOG.info("COMMAND : SAVE");
						  
						  user = userService.getUserByFullname( req.getPayload().getFullname() );
						  if ( user != null ) {
							  
							  statusTrx = StatusCode.ALREDY_REGISTERD;
							  
						  } else {
							  
							  boolean isUserSaved = userService.insertUser( req.getPayload() );
							  if ( isUserSaved == false ) {
								  
								  statusTrx = StatusCode.FAILED_PROCESS;
								  
							  }  else {
								  
								  user = userService.getUserByFullname( req.getPayload().getFullname() );
								  String message = MailHelper.registrationUserMessage( user );
								  mailService.sendEmail(user.getEmail(), mailTitleRegisterdAccount, message, null, null);
								  String value2 = Constant.VALUE_INSERT_USER.replaceAll("<fullname>", user.getFullname()).replaceAll("<joinDate>", Util.formatDate(new Date()));
								  auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_USER_REGISTRATION, req.getHeader().getuName(), value2) );
								  userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
							  
							  }
							  
						  }
						  
						  break;
					  case "update":
						  
						  LOG.info("COMMAND : UPDATE");
						  
						  user = userService.getUserByID( req.getPayload().getId() );
						  if (user != null) {
							  
							  boolean isUpdateUser = userService.updateUser( req.getPayload() );
							  if (isUpdateUser) {
								  
								  String value2 = Constant.VALUE_UPDATE_USER.replaceAll("<fullname>", user.getFullname()).replaceAll("<email>", user.getEmail())
										  .replaceAll("<phone_no>", user.getPhone_no()).replaceAll("<address>", user.getAddress());
								  auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_USER_UPDATE, req.getHeader().getuName(), value2 ) );
								  userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
							  
							  } else {
								  
								  statusTrx = StatusCode.FAILED_PROCESS;
								  
							  }
							  
						  } else {
							  
							  statusTrx = StatusCode.DATA_NOT_FOUND;
							  
						  }
						  
						  break;
					  case "changeStatus":
						  
						  LOG.info("COMMAND : CHANGE STATUS");
						  
						  user = userService.getUserByID( req.getPayload().getId() );
						  if (user != null) {
							  
							  boolean isChanged = userService.changeStatusUser( req.getPayload() );
							  if (isChanged) {
								  
								  String value2 = Constant.VALUE_CHANGE_STATUS_USER.replaceAll("<fullname>", user.getFullname()).replaceAll("<status>", user.getStatus());
								  auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_USER_STATUS_UPDATE, req.getHeader().getuName(), value2 ) );
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
				
				statusTrx = StatusCode.USER_NOT_FOUND;
				
			} else { 
				
				switch ( req.getHeader().getCommand() ) { 
				
				  case "save":
					  
					  LOG.info("COMMAND : SAVE");
					  
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
							  
							  User user = userService.getUserByID(req.getPayload().getUserId());
							  CheckUpRecord cRecord = checkUpService.getCheckUpRecord(user.getId(), child.getId(), "ACT_000");
							  GrowthDtl growth = checkUpService.getGrowthDtl( "ACT_000", cRecord.getId());
							  CheckUpMaster cm = masterService.getMstCheckUpByCode( "ACT_000" );
							  String checkUpDate = Util.formatDate(checkHealth.getCheckUpDate());
							  String weightCategory = masterService.category(Constant.WEIGHT, cm.getBatch(), growth.getWeight());
							  String lengthCategory = masterService.category(Constant.LENGTH, cm.getBatch(), growth.getLength());
							  String headDiameterCategory = masterService.category(Constant.HEAD_CIRCUMFERENCE, cm.getBatch(), growth.getHeadDiameter());					
							  
							  Map<String, Object> object = new HashMap<String, Object>();
							  SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
							  object.put("parentName", user.getFullname());
							  object.put("address", user.getAddress());
							  object.put("childName", child.getFullname());
							  object.put("age",  String.valueOf(Util.calculateMonth(formatDate.format(child.getBirthDate()), formatDate.format(new Date()))) + " Bulan") ;
							  object.put("birthDate", formatDate.format(child.getBirthDate()));
							  object.put("checkUpDate", Util.formatDate(cRecord.getCheckUpDate()));
							  object.put("weight", String.valueOf(growth.getWeight()) + " KG");
							  object.put("length", String.valueOf(growth.getLength()) + " CM");
							  object.put("headDiameter", String.valueOf(growth.getHeadDiameter()) + " CM");
							  object.put("weightNotes", Util.getWeightNotes (weightCategory) );
							  object.put("lengthNotes", Util.getLengthNotes (lengthCategory));
							  object.put("headDiameterNotes", Util.getHeadDiameterNotes (headDiameterCategory));
							  byte[] pdfAsByte = ExportPDF.download(object, "report/template_checkup.jrxml");
								
							  String message = MailHelper.registrationChildMessage(user.getFullname(), child.getFullname());
							  String filename = "Laporan Pemeriksaan Medis " + child.getFullname() + "(" + checkUpDate + ").pdf";
							  mailService.sendEmail(user.getEmail(), mailTitleRegisterdChild, message, filename, pdfAsByte);
						      
							  String value2 = Constant.VALUE_RECORD_CHECK_UP.replaceAll("<childName>", child.getFullname()).replaceAll("<weight>", String.valueOf(growth.getWeight()))
						    		  .replaceAll("<length>", String.valueOf(growth.getLength())).replaceAll("<headDiameter>", String.valueOf(growth.getHeadDiameter()));
							  auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_CHILD_REGISTRATION, req.getHeader().getuName(), value2 ) );
						  }
						  
						  userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
					  
					  } else {
						  statusTrx = StatusCode.FAILED_PROCESS;
					  }
					  
					  break;
					  
				  case "update":
					  
					  LOG.info("COMMAND : UPDATE");
					  
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
							  auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_CHILD_UPDATE, req.getHeader().getuName(), value2 ) );
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

