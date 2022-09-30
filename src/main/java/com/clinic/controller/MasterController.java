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

import com.clinic.api.object.HeaderRequest;
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.VaccineRequest;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.Constant;
import com.clinic.constant.StatusCode;
import com.clinic.entity.AuditTrail;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.User;
import com.clinic.entity.UserAdmin;
import com.clinic.service.AuditTrailService;
import com.clinic.service.MasterService;
import com.clinic.service.UserAdminService;
import com.clinic.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/mst")
public class MasterController extends BaseController {
	
	private static final Logger LOG = LogManager.getLogger(MasterController.class);
	
	@Autowired
	MasterService masterService;

	@Autowired 
	UserService userService;
	
	@Autowired
	UserAdminService userAdminService;

	@Autowired
	AuditTrailService auditTrailService;
	
	@RequestMapping(value = "/getListMst", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getListMst(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > result = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		
		try{
			
			HeaderRequest req = (HeaderRequest) ObjectMapper(input, HeaderRequest.class);
			
			if ( req.getChannel().equals( Constant.CHANNEL_WEB ) ) {
				
				UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getuName() );
				if (userAdmin == null) {
					
					statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
					
				} else {
					
					switch ( req.getCommand() ) {
					case "info-list-vaccine":
						
						LOG.info("COMMAND : INFO-LIST-VACCINE");
						result.put("object", masterService.getListMstVaccine());
						
						break;
					case "info-list-checkup":
						
						LOG.info("COMMAND : INFO-LIST-VACCINE");
						result.put("object", masterService.getListMstCheckUp());
						
						break;
					default:
						break;
					}
					
				}
				
				userAdminService.updateLastActivity(userAdmin);
				
			} else if ( req.getChannel().equals( Constant.CHANNEL_MOBILE ) ) {
				
				User user = userService.getUserByUsername( req.getuName() );
				if (user == null) {
					
					statusTrx = StatusCode.USER_NOT_VALID;
					
				} if (!user.getStatus().equals("ACTIVE")) {
					
					statusTrx = StatusCode.USER_NOT_VALID;
					
				} else {
					
					switch ( req.getCommand() ) {
					case "info-list-vaccine":
						
						LOG.info("COMMAND : INFO-LIST-VACCINE");
						result.put("object", masterService.getListMstVaccine());
						
						break;
					case "info-list-checkup":
						
						LOG.info("COMMAND : INFO-LIST-VACCINE");
						result.put("object", masterService.getListMstCheckUp());
						
						break;
					default:
						break;
					}
					
				}
				
				user.setLastActivity(new Date());
				userService.updateLastActivity(user);
				
			}
			
			response.setPayload(result);
			
		}catch (Exception e){
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}
	
	@RequestMapping(value = "/processMstVaccine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> processMstVaccine(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		String value2 = null;
		boolean result = false;
		
		try{
			
			LOG.info("PROCESS MST VACCINE");
			APIRequest < VaccineRequest > req = getVaccineRq( input );
			UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			
			if ( req.getHeader().getChannel().equals( Constant.CHANNEL_WEB ) ) {
				
				if (userAdmin == null) {
					
					statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
					
				} else {
					
					switch ( req.getHeader().getCommand() ) {
					case "save":
						
						LOG.info("COMMAND : SAVE");
						result = masterService.addVaccineMaster( req.getPayload() );
						if (result) {
							value2 = Constant.VALUE_INSERT_MST_VACCINE.replaceAll("<vaccineName>", req.getPayload().getVaccineName()).
									replaceAll("<vaccineType>", req.getPayload().getVaccineType()).
									replaceAll("<expDays>", String.valueOf(req.getPayload().getExpDays())).
									replaceAll("<batch>", String.valueOf(req.getPayload().getBatch()));
							auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_ADD_MST_VACCINE, req.getHeader().getuName(), value2 ) );
						} 
						
						break;
					case "update":
						
						LOG.info("COMMAND : UPDATE");
						result = masterService.updateVaccineMaster( req.getPayload() );
						if (result) {
							value2 = Constant.VALUE_UPDATE_MST_VACCINE.replaceAll("<vaccineName>", req.getPayload().getVaccineName()).
									replaceAll("<vaccineType>", req.getPayload().getVaccineType()).
									replaceAll("<expDays>", String.valueOf(req.getPayload().getExpDays())).
									replaceAll("<notes>", String.valueOf(req.getPayload().getNotes()));
							auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_UPDATE_MST_VACCINE, req.getHeader().getuName(), value2 ) );
						} 
						
						break;
					case "changeStatus":
						
						LOG.info("COMMAND : CHANGE STATUS");
						result = masterService.changeStatusVaccineMaster(req.getPayload());
						if (result) {
							value2 = Constant.VALUE_UPDATE_MST_VACCINE.replaceAll("<vaccineName>", req.getPayload().getVaccineName()).
									replaceAll("<status>", req.getPayload().getStatus());
							auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_CHANGE_STATUS_MST_VACCINE, req.getHeader().getuName(), value2 ) );
						} 
						
						break;
					default:
						break;
					}
					
					if (!result) {
						statusTrx = StatusCode.FAILED_PROCESS;
					}
					
					userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
				}
				
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
	
	@RequestMapping(value = "/processMstCheckUp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> processMstCheckUp(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		String value2 = null;
		boolean result = false;
		
		try{
			
			LOG.info("PROCESS MST CHECKUP");
			APIRequest < CheckUpMaster > req = getCheckUpMaster(input);
			UserAdmin user = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			
			if ( req.getHeader().getChannel().equals( Constant.CHANNEL_WEB ) ) {
				
				if (user == null) {
					
					statusTrx = StatusCode.USER_NOT_FOUND;
					
				} else {
					
					switch ( req.getHeader().getCommand() ) {
					case "save":
						
						LOG.info("COMMAND : SAVE");
						result = masterService.addCheckUpMaster(req.getPayload());
						if (result) {
							value2 = Constant.VALUE_INSERT_CHECKUP.replaceAll("<actName>", req.getPayload().getActName()).
									replaceAll("<batch>", String.valueOf(req.getPayload().getBatch())).
									replaceAll("<nextDays>", String.valueOf(req.getPayload().getNextCheckUpDays()));
							auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_ADD_MST_CHECK_UP, req.getHeader().getuName(), value2 ));
						} 
						
						break;
					case "update":
						
						LOG.info("COMMAND : UPDATE");
						result = masterService.updateCheckUpMaster(req.getPayload());
						if (result) {
							value2 = Constant.VALUE_UPDATE_CHECKUP.replaceAll("<actName>", req.getPayload().getActName()).
									replaceAll("<batch>", String.valueOf(req.getPayload().getBatch())).
									replaceAll("<nextDays>", String.valueOf(req.getPayload().getNextCheckUpDays()));
							auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_UPDATE_MST_CHECK_UP, req.getHeader().getuName(), value2 ) );
						} 
						
						break;
					case "changeStatus":
						
						LOG.info("COMMAND : CHANGE STATUS");
						result = masterService.changeStatusCheckUpMaster(req.getPayload());
						if (result) {
							value2 = Constant.VALUE_CHANGE_STATUS_CHECKUP.replaceAll("<actName>", req.getPayload().getActName()).
									replaceAll("<status>", req.getPayload().getStatus());
							auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_CHANGE_STATUS_MST_CHECK_UP, req.getHeader().getuName(), value2 ) );
						} 
						
						break;
					default:
						break;
					}
					
					if (!result) {
						
						statusTrx = StatusCode.FAILED_PROCESS;
						
					}
					
					userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
				
				}
				
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
