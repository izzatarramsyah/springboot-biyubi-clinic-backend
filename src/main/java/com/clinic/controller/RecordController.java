package com.clinic.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.InfoRq;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.StatusCode;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.VaccineMaster;
import com.clinic.service.CheckUpService;
import com.clinic.service.MasterService;
import com.clinic.service.UserService;
import com.clinic.service.VaccineService;
import com.clinic.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(RecordController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	MasterService masterService;
	
	@Autowired
	VaccineService vaccineService;
	
	@Autowired
	CheckUpService checkHealthService;
		
	@RequestMapping(value = "/getSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getSchedule(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap < String, Object > result = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		String responseMsg = StatusCode.SUCCESS.toString();
		String command = null; String uName = null; int userId; int childId;
		try{
			APIRequest < InfoRq > req = getInfoRq( input );
			command = req.getHeader().getCommand();
			uName = req.getHeader().getuName();
			userId = req.getPayload().getUserId();
			childId = req.getPayload().getChildId();
			User user = userService.getUserByUsername(uName);
			if (user == null) {
				statusTrx = StatusCode.INVALID;
				responseMsg = StatusCode.INVALID.toString();
				result.put("message", "User not found");
			} else {
				switch ( command ) { 
				  case "info-schedule-vaccine":
					result.put("object", vaccineService.getSchedule(userId, childId));
				    break;
				  case "info-schedule-checkup":
				    result.put("object", checkHealthService.getSchedule(userId, childId));
				    break;
				  default:
					break;
				}
			}
			response.setPayload(result);
			if (user != null) userService.updateLastActivity(user);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
			responseMsg = StatusCode.GENERIC_ERROR.toString();
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), responseMsg));
		LOG.traceExit();
		return response;
	}
	
}
