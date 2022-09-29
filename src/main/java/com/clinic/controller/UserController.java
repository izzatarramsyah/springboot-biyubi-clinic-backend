package com.clinic.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.UserData;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.Constant;
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
		HashMap< String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		
		try {
			
			LOG.info("LOGIN");
			APIRequest<User> req = getRequestUser(input);
			
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_MOBILE )) {
				
				Boolean isValid = userService.checkValidUser(req.getPayload().getUsername(), req.getPayload().getPassword());
				if (!isValid) {
					
					statusTrx = StatusCode.USER_NOT_FOUND;
				
				}  else {
					
					User user = userService.getUserByUsername( req.getPayload().getUsername() );
					UserData usrData = new UserData().setAttribute(user);
					List < ChildData > listChildData = new ArrayList < ChildData > ();
					
					for (Child child : userService.getChildByUserID( user.getId()) ) { 
						
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
							String weightCategory = masterService.category(Constant.WEIGHT, batch, seriesWeight.get(index));
							childData.setWeight( seriesWeight.get(index) );
							childData.setWeightCategory( weightCategory );
							childData.setWeightNotes( Util.getWeightNotes(weightCategory) );
						}
						  
						if (seriesLength.size() > 0) {
							int index = seriesLength.size() - 1;
							String lengthCategory = masterService.category(Constant.LENGTH, batch, seriesLength.get(index));
							childData.setLength( seriesLength.get(index) );
							childData.setLengthCategory( lengthCategory );
							childData.setLengthNotes( Util.getLengthNotes(lengthCategory) );
						 }
						 
						if (seriesHeadDiameter.size() > 0) {
							int index = seriesHeadDiameter.size() - 1;
							String headDiameterCategory = masterService.category(Constant.HEAD_CIRCUMFERENCE, batch, seriesHeadDiameter.get(index));
							childData.setHeadDiameter( seriesHeadDiameter.get(index) );
							childData.setHeadDiameterCategory( headDiameterCategory );
							childData.setHeadDiameterNotes( Util.getHeadDiameterNotes(headDiameterCategory) );
						}		
						
						listChildData.add(childData);
						
					}
					
					usrData.setChildData(listChildData);
					responseObject.put("object", usrData);
					userService.updateLastActivity(user);
				}
				
			}

			response.setPayload(responseObject);

		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.debug("RES::[{}]:{}", response);
		LOG.traceExit();
		return response;
		
	}
	
}
