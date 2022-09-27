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

import com.clinic.api.object.AppConfig;
import com.clinic.api.object.Graph;
import com.clinic.api.object.HeaderRequest;
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.InfoRq;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.StatusCode;
import com.clinic.entity.ApplicationConfig;
import com.clinic.entity.User;
import com.clinic.service.ApplicationConfigService;
import com.clinic.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/config")
public class ApplicationConfigController extends BaseController {
	
	private static final Logger LOG = LogManager.getLogger(ApplicationConfigController.class);
	
	@Autowired
	ApplicationConfigService service;
	
	@Autowired 
	UserService userService;
	
	@RequestMapping(value = "/getGraph", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getListMst(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > result = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		try{
			APIRequest < AppConfig > req = getAppConfig( input );
			User user = userService.getUserByUsername(req.getHeader().getuName());
			if (user == null) {
				statusTrx = StatusCode.USER_NOT_VALID;
			} else {
				List < ApplicationConfig > listConfig = service.get(req.getPayload().getAppName(), req.getPayload().getSvcName());
				List <Graph> listGraph = new ArrayList <Graph>();
				for (ApplicationConfig conf : listConfig) {
					Graph graph = new Graph();
					String[] paramName = conf.getParamName().split("_");
					String paramType = paramName[0];
					int paramMonth = Integer.parseInt(paramName[1]);
					String[] paramValue = conf.getParamValue().split("_");
					double paramVal = Double.parseDouble(paramValue[0]);
					String paramCategory = paramValue[1];
					graph.setType(paramType);
					graph.setMonth(paramMonth);
					graph.setCategogry(paramCategory);
					graph.setValue(paramVal);
					listGraph.add(graph);
				}
				result.put("object", listGraph);
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
