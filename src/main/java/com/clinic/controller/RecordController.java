package com.clinic.controller;


import java.util.Date;
import java.util.HashMap;

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
import com.clinic.entity.User;
import com.clinic.service.CheckUpService;
import com.clinic.service.MasterService;
import com.clinic.service.UserService;
import com.clinic.service.VaccineService;

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
		HashMap < String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		try {
			LOG.info("GET SCHEDULE");
			APIRequest < InfoRq > req = getInfoRq( input );
			User user = userService.getUserByUsername( req.getHeader().getuName() );
			if (user == null) {
				statusTrx = StatusCode.USER_NOT_FOUND;
			} if (!user.getStatus().equals("ACTIVE")) {
				statusTrx = StatusCode.USER_NOT_VALID;
			} else {
				switch ( req.getHeader().getCommand() ) { 
				  case "info-schedule-vaccine":
					  LOG.info("COMMAND : INFO-SCHEDULE-VACCINE");
					  responseObject.put("object", vaccineService.getSchedule(req.getPayload().getUserId(), req.getPayload().getChildId()));
					  break;
				  case "info-schedule-checkup":
					  LOG.info("COMMAND : INFO-SCHEDULE-CHECKUP");
					  responseObject.put("object", checkHealthService.getSchedule(req.getPayload().getUserId(), req.getPayload().getChildId()));
					  break;
				  default:
					  break;
				}
				user.setLastActivity(new Date());
				userService.updateLastActivity(user);
				response.setPayload(responseObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}

}
