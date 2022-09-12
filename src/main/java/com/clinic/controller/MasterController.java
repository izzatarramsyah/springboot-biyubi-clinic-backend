package com.clinic.controller;

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

import com.clinic.api.object.HeaderRequest;
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.InfoRq;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.StatusCode;
import com.clinic.entity.User;
import com.clinic.entity.VaccineMaster;
import com.clinic.service.MasterService;
import com.clinic.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/mst")
public class MasterController extends BaseController {
	
	private static final Logger LOG = LogManager.getLogger(MasterController.class);
	
	@Autowired
	MasterService masterService;

	@Autowired 
	UserService userService;
	
	@RequestMapping(value = "/getListMst", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getListMst(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > result = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		String uName = null;
		String command = null;
		try{
			HeaderRequest req = (HeaderRequest) ObjectMapper(input, HeaderRequest.class);
			uName = req.getuName();
			command = req.getCommand();
			User user = userService.getUserByUsername(uName);
			if (user == null) {
				statusTrx = StatusCode.USER_NOT_VALID;
			} else {
				switch ( command ) {
				case "info-list-vaccine":
					result.put("object", masterService.getListMstVaccine());
					break;
				case "info-list-checkup":
					result.put("object", masterService.getListMstCheckUp());
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
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}
}
