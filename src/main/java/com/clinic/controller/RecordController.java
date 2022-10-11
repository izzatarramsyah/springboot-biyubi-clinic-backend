package com.clinic.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.InfoRq;
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
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineRecord;
import com.clinic.report.ExportPDF;
import com.clinic.service.AuditTrailService;
import com.clinic.service.CheckUpService;
import com.clinic.service.MailService;
import com.clinic.service.MasterService;
import com.clinic.service.UserAdminService;
import com.clinic.service.UserService;
import com.clinic.service.VaccineService;
import com.clinic.util.MailHelper;
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
	UserAdminService userAdminService;
	
	@Autowired
	MasterService masterService;
	
	@Autowired
	VaccineService vaccineService;
	
	@Autowired
	CheckUpService checkUpService;

	@Autowired
	MailService mailService;
	
//	@Autowired
//	AuditTrailService auditTrailService;
	
	@RequestMapping(value = "/vaccineRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> vaccineRecord(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		try {
			LOG.info("VACCINE RECORD");
			APIRequest < VaccineRecord > req = getRequestVaccineRecord(input);
			UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				if (userAdmin == null) {
					statusTrx = StatusCode.USER_NOT_FOUND;
				} else {
					VaccineRecord vaccineRecord = req.getPayload();
					VaccineRecord check = vaccineService.getVaccineRecord(vaccineRecord.getUserId(), vaccineRecord.getChildId(), 
							vaccineRecord.getBatch(), vaccineRecord.getVaccineCode());
					VaccineMaster vm = masterService.getMstVaccineByCode( vaccineRecord.getVaccineCode() );
					User user = userService.getUserByID( vaccineRecord.getUserId() );
					if (user == null) {
						statusTrx = StatusCode.USER_NOT_FOUND;
					} else {
						Child child = userService.getChildByID( vaccineRecord.getChildId() );
						if ( child != null) {
							switch ( req.getHeader().getCommand() ) {
							case Constant.SAVE_VACCINE_RECORD:
								if (check == null) {
									if (vaccineService.addVaccineRecord( vaccineRecord ) ){
										String value2 = Constant.VALUE_RECORD_VACCINE.replaceAll("<childName>", child.getFullname()).replaceAll("<vaccineName>", vm.getVaccineName())
												.replaceAll("<batch>", String.valueOf(vaccineRecord.getBatch())).replaceAll("<notes>", vaccineRecord.getNotes());
										//auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_ADD_VACCINE_RECORD, req.getHeader().getuName(), value2) );
									}
								} else {
									statusTrx = StatusCode.RECORD_ALREADY_INSERTED;
								}
								break;
							case Constant.UPDATE_VACCINE_RECORD:
								if (check != null) {
									if ( vaccineService.updateVaccineRecord( vaccineRecord ) ) {
										String value2 = Constant.VALUE_RECORD_VACCINE.replaceAll("<childName>", child.getFullname()).replaceAll("<vaccineName>", vm.getVaccineName())
												.replaceAll("<batch>", String.valueOf(vaccineRecord.getBatch())).replaceAll("<notes>", vaccineRecord.getNotes());
										//auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_UPDATE_VACCINE_RECORD, req.getHeader().getuName(), value2) );
									}
								} else {
									statusTrx = StatusCode.FAILED_PROCESS;
								}
								break;
							default:
								break;
							}
						} else {
							statusTrx = StatusCode.FAILED_PROCESS;
						}
					}
				}
			}
			userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
			response.setPayload(responseObject);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		response.setHeader(new HeaderResponse (statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}
		
	@RequestMapping(value = "/checkUpRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> checkUpRecord(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		try {
			LOG.info("CHECKUP RECORD");
			APIRequest < CheckUpRequest > req = getCheckUpRequest(input);
			UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				if (userAdmin == null) {
					statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
				} else {
					int userId = req.getPayload().getUserId();
					int childId = req.getPayload().getChildId();
					String mstCode = req.getPayload().getMstCode();
					CheckUpRecord check = checkUpService.getCheckUpRecord( userId, childId, mstCode);
					User user = userService.getUserByID( userId );
					if (user == null) {
						statusTrx = StatusCode.USER_NOT_FOUND;
					} else {
						Child child = userService.getChildByID( childId );
						if ( child != null) {
							switch ( req.getHeader().getCommand() ) {
							case Constant.SAVE_CHECKUP_RECORD:
								if ( check == null ) {
									if (checkUpService.addCheckUpRecord( req.getPayload() )) {
										CheckUpRecord record = checkUpService.getCheckUpRecord(user.getId(), child.getId(), mstCode);
										GrowthDtl growth = checkUpService.getGrowthDtl( mstCode, record.getId());
										String value2 = Constant.VALUE_RECORD_CHECK_UP.replaceAll("<childName>", child.getFullname()).replaceAll("<weight>", String.valueOf(growth.getWeight()))
									    		  .replaceAll("<length>", String.valueOf(growth.getLength())).replaceAll("<headDiameter>", String.valueOf(growth.getHeadDiameter()));
										//auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_ADD_CHECK_UP_RECORD, req.getHeader().getuName(), value2 ) );
									}
								} else {
									statusTrx = StatusCode.RECORD_ALREADY_INSERTED;
								}
								break;
							case Constant.UPDATE_CHECKUP_RECORD:
								if ( check != null ) {
									if (checkUpService.updateGrowthDtl( req.getPayload() )) {
										CheckUpRecord record = checkUpService.getCheckUpRecord(user.getId(), child.getId(), mstCode);
										GrowthDtl growth = checkUpService.getGrowthDtl( mstCode, record.getId());
										String value2 = Constant.VALUE_RECORD_CHECK_UP.replaceAll("<childName>", child.getFullname()).replaceAll("<weight>", String.valueOf(growth.getWeight()))
									    		  .replaceAll("<length>", String.valueOf(growth.getLength())).replaceAll("<headDiameter>", String.valueOf(growth.getHeadDiameter()));
										//auditTrailService.saveAuditTrail(new AuditTrail( Constant.ACTIVITY_UPDATE_CHECK_UP_RECORD, req.getHeader().getuName(), value2 ) );
									}
								} else { 
									statusTrx = StatusCode.FAILED_PROCESS;
								}
								break;
							default:
								break;
							}
						} else {
							statusTrx = StatusCode.FAILED_PROCESS;
						}
					}
				}
			}
			userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
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
	
	@RequestMapping(value = "/getSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getSchedule(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap < String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS;
		
		try {
			
			LOG.info("GET SCHEDULE");
			APIRequest < InfoRq > req = getInfoRq( input );
			
			if ( req.getHeader().getChannel().equals( Constant.CHANNEL_WEB ) ) {
				
				UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
				if (userAdmin == null) {
					
					statusTrx = StatusCode.USER_NOT_FOUND;
					
				} else {
					
					switch ( req.getHeader().getCommand() ) { 
					case "info-schedule-vaccine":
						LOG.info("COMMAND : INFO-SCHEDULE-VACCINE");
						responseObject.put("object", vaccineService.getSchedule(req.getPayload().getUserId(), req.getPayload().getChildId()));
						break;
					case "info-schedule-checkup":
						LOG.info("COMMAND : INFO-SCHEDULE-CHECKUP");
						responseObject.put("object", checkUpService.getSchedule(req.getPayload().getUserId(), req.getPayload().getChildId()));
						break;
					default:
						break; 
					}
					userAdminService.updateLastActivity(userAdmin);
				}
				
			} else if ( req.getHeader().getChannel().equals( Constant.CHANNEL_MOBILE ) ) {
				
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
						responseObject.put("object", checkUpService.getSchedule(req.getPayload().getUserId(), req.getPayload().getChildId()));
						break;
					default:
						break; 
					}
					
					user.setLastActivity(new Date());
					userService.updateLastActivity(user);
					
				}
				
			}
			
			response.setPayload(responseObject);
			
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
