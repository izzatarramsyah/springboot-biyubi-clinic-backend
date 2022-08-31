package com.clinic.controller;

import java.text.SimpleDateFormat;
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

import com.clinic.api.object.ChildData;
import com.clinic.api.object.GrowthDtlRs;
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.UserData;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.StatusCode;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.CheckUpRecord;
import com.clinic.entity.Child;
import com.clinic.entity.GrowthDtl;
import com.clinic.entity.User;
import com.clinic.service.CheckUpService;
import com.clinic.service.MailService;
import com.clinic.service.MasterService;
import com.clinic.service.UserService;
import com.clinic.util.Graph;
import com.clinic.util.MailHelper;
import com.clinic.util.Security;
import com.clinic.util.Util;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	
	private static final Logger LOG = LogManager.getLogger(UserController.class);

	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired 
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	CheckUpService checkUpService;

	@Autowired
	MasterService masterService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> login(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > result = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		String responseMsg = StatusCode.SUCCESS.toString();
		String username = null; String password;
		try{
			APIRequest<User> req = getRequestUser(input);
			username = req.getPayload().getUsername();
			password = req.getPayload().getPassword();
			LOG.info("REQ::{}", req.toString());
			Boolean isValid = userService.checkValidUser(username, password);
			if (!isValid) {
				statusTrx = StatusCode.INVALID;
				responseMsg = StatusCode.INVALID.toString();
				result.put("message", "User Is Not Valid");
			} else {
				User user = userService.getUserByUsername(username);
				UserData usrData = new UserData().setAttribute(user);
				
				List < ChildData > listChildData = new ArrayList < ChildData > ();
				for (Child lst : userService.getChildByUserID( user.getId()) ) { 
					  
					  String currentDate = formatDate.format(new Date());
					  String birthDate = formatDate.format(lst.getBirthDate());
					  long age = Util.calculateMonth(birthDate, currentDate);
					  ChildData childData = new ChildData().setAttribute(lst);
					  childData.setAge(age);
					  
					  List <GrowthDtlRs> listDtl = new ArrayList<GrowthDtlRs>();
					  for (CheckUpRecord checkUpRecord: checkUpService.getListCheckHealth(lst.getUserId(), lst.getId())) {
						  GrowthDtlRs dtl = new GrowthDtlRs();
						  CheckUpMaster mst = masterService.getMstCheckUpByCode(checkUpRecord.getMstCode());
						  dtl.setMstCode(mst.getCode());
						  dtl.setDescription(mst.getDescription());
						  dtl.setBatch(mst.getBatch());
						  GrowthDtl growthDtl = checkUpService.getGrowthDtl(checkUpRecord.getMstCode(), checkUpRecord.getId());
						  dtl.setWeight(growthDtl.getWeight());
						  dtl.setLength(growthDtl.getLength());
						  dtl.setHeadDiameter(growthDtl.getHeadDiameter());
						  listDtl.add(dtl);
					  }
					  childData.setGrowthDetail(listDtl);
					  
					  if ( listDtl.size() > 0 ) {
						  int index = listDtl.size() - 1;
						  childData.setWeightCategory(Graph.categorizeWeight(age,  childData.getGrowthDetail().get(index).getWeight()));
						  childData.setWeightNotes(Graph.getWeightNotes(childData.getWeightCategory()));
						  childData.setLengthCategory(Graph.categorizeLength(age, childData.getGrowthDetail().get(index).getLength()));
						  childData.setLengthNotes(Graph.getLengthNotes(childData.getLengthCategory()));
						  childData.setHeadDiameterCategory(Graph.categorizeHeadDiameter(age, childData.getGrowthDetail().get(index).getHeadDiameter()));
						  childData.setHeadDiameterNotes(Graph.getHeadDiameterNotes(childData.getHeadDiameterCategory()));
					  }
					  
					  listChildData.add(childData);				 
				}
				usrData.setChildData(listChildData);
				result.put("message", "Login Success");
				result.put("object", usrData);
				userService.updateLastActivity(user);
			}
			response.setPayload(result);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
			responseMsg = StatusCode.GENERIC_ERROR.toString();
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), responseMsg));
		LOG.debug("RES::[{}]:{}", response);
		LOG.traceExit();
		return response;
	}
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> forgotPassword(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap < String, Object > result = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		String responseMsg = StatusCode.SUCCESS.toString();
		try{
			APIRequest < User > req = getRequestUser(input);
			LOG.info("REQ::{}", req.toString());
			User user = userService.getUserByUsername(req.getPayload().getUsername());
			if (user == null) {
				statusTrx = StatusCode.INVALID;
				responseMsg = StatusCode.INVALID.toString();
				result.put("message", "User Not Found");
			} else {
				if (!user.getEmail().equals(req.getPayload().getEmail())) {
					statusTrx = StatusCode.INVALID;
					responseMsg = StatusCode.INVALID.toString();
					result.put("message", "Email not match");
				} else if (!user.getPhone_no().equals(req.getPayload().getPhone_no())) {
					statusTrx = StatusCode.INVALID;
					responseMsg = StatusCode.INVALID.toString();
					result.put("message", "Phone number not match");
				} else {
					String password = Security.decrypt(user.getPassword());
					String message = MailHelper.forgotPasswordMessage(user.getFullname(), user.getUsername(), password);
					mailService.sendEmail(user.getEmail(), "Lupa password Biyubi App", message, "");
					result.put("message", "Send password Success");
				}
			}
			response.setPayload(result);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
			responseMsg = StatusCode.GENERIC_ERROR.toString();
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), responseMsg));
		LOG.debug("RES::[{}]:{}", response);
		LOG.traceExit();
		return response;
	}
	
}
