package com.clinic.controller;

import java.text.ParseException;
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

import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.InfoRq;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.Constant;
import com.clinic.constant.StatusCode;
import com.clinic.entity.AuditTrail;
import com.clinic.service.AuditTrailService;
import com.clinic.service.UserAdminService;

@CrossOrigin
@RestController
@RequestMapping("/auditTrail")
public class AuditTrailController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(AuditTrailController.class);

	@Autowired
	AuditTrailService auditTrailService;
	
	@Autowired
	UserAdminService userAdminService;

	@RequestMapping(value = "/listActivity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> listActivity(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObj = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List < AuditTrail > listAuditTrail = new ArrayList < AuditTrail >();
		Date startDate 	= null;
		Date endDate 	= null;
		
		try{
			
			LOG.info("GET LIST AUDIT TRAIL");
			APIRequest < InfoRq > req = getInfoRq( input );
			
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				
				try {
					startDate = sdf.parse(req.getPayload().getStartDate() + " 00:00:00");
				} catch (ParseException e) {
					LOG.error("ERR :: {} " +e);
				}
				
				try {
					endDate = sdf.parse(req.getPayload().getEndDate() + " 23:59:59");
				} catch (ParseException e) {
					LOG.error("ERR :: {} " +e);
				}

				listAuditTrail = auditTrailService.getAuditTrail(req.getPayload().getUsername(), startDate, endDate);
				responseObj.put("object", listAuditTrail);
				response.setPayload(responseObj);
				
				userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
				
			}

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
