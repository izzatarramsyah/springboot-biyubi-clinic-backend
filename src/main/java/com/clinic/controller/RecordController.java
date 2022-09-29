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
	
	@Value("${mail.titile.vaccine.record}")
	private String mailTitleVaccineRecord;
	
	@Value("${mail.titile.checkup.record}")
	private String mailTitleCheckUpRecord;
	
	@Value("${mail.titile.edit.vaccine.record}")
	private String mailTitleEditVaccineRecord;
	
	@Value("${mail.titile.edit.checkup.record}")
	private String mailTitleEditCheckUpRecord;
	
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
	
	@Autowired
	AuditTrailService auditTrailService;
	
	@RequestMapping(value = "/vaccineRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> vaccineRecord(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		boolean result = false;
		String activity = null;
		
		try {
			
			LOG.info("VACCINE RECORD");
			APIRequest < VaccineRecord > req = getRequestVaccineRecord(input);
			UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				
				if (userAdmin == null) {
					
					statusTrx = StatusCode.USER_NOT_FOUND;
					
				} else {
					
					VaccineRecord vaccineRecord = req.getPayload();
					
					switch ( req.getHeader().getCommand() ) {
					case "save":
						
						LOG.info("SAVE");

						vaccineRecord.setCreatedDtm( new Date() );
						vaccineRecord.setCreatedBy( "SYSTEM" );
						result = vaccineService.addVaccineRecord( vaccineRecord );
						activity = Constant.ACTIVITY_ADD_VACCINE_RECORD;
						
						break;
					case "update":
						
						LOG.info("UPDATE");

						vaccineRecord.setUpdatedDtm( new Date() );
						vaccineRecord.setUpdatedBy( "SYSTEM" );
						result = vaccineService.updateVaccineRecord( vaccineRecord );
						activity = Constant.ACTIVITY_UPDATE_VACCINE_RECORD;
						
						break;
					default:
						break;
					}
					
					if (!result) {
						statusTrx = StatusCode.FAILED_PROCESS;
					} else {
						
						vaccineRecord = vaccineService.getVaccineRecord(vaccineRecord.getUserId(), vaccineRecord.getChildId(), 
								vaccineRecord.getBatch(), vaccineRecord.getVaccineCode());
						new ObjectMapper().writer().writeValueAsString( vaccineRecord );
						User user = userService.getUserByID(vaccineRecord.getUserId());
						Child child = userService.getChildByID(vaccineRecord.getChildId());
						VaccineMaster vm = masterService.getMstVaccineByCode(vaccineRecord.getVaccineCode());
		
						Map<String, Object> object = new HashMap<String, Object>();
						object.put("parentName", user.getFullname());
						object.put("address", user.getAddress());
						object.put("childName", child.getFullname());
						object.put("age",  String.valueOf(Util.calculateMonth(formatDate.format(child.getBirthDate()), formatDate.format(new Date()))) + " Bulan") ;
						object.put("birthDate", formatDate.format(child.getBirthDate()));
						object.put("vaccineDate", Util.formatDate(vaccineRecord.getVaccineDate()));
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						Calendar expDate = Calendar.getInstance();
						expDate.setTime(vaccineRecord.getVaccineDate());
						expDate.add(Calendar.DATE, vm.getExpDays());
						object.put("expDate", Util.formatDate(sdf.parse(sdf.format(expDate.getTime()))));
						object.put("vaccineName", vm.getVaccineName());
						object.put("batch", String.valueOf(vaccineRecord.getBatch()));
						object.put("vaccineNotes", vaccineRecord.getNotes());
						byte[] pdfAsByte = ExportPDF.download(object, "report/template_vaccine.jrxml");
						
						String message = MailHelper.vaccineMessage(user.getFullname());
						String filename = "Laporan Imunisasi " + child.getFullname() + "(" + Util.formatDate(vaccineRecord.getVaccineDate()) + ").pdf";
						mailService.sendEmail(user.getEmail(), mailTitleEditVaccineRecord, message, filename, pdfAsByte);
						
						String value2 = Constant.VALUE_RECORD_VACCINE.replaceAll("<childName>", child.getFullname()).replaceAll("<vaccineName>", vm.getVaccineName()).replaceAll("<batch>", String.valueOf(vaccineRecord.getBatch())).replaceAll("<notes>", vaccineRecord.getNotes());
						auditTrailService.saveAuditTrail(new AuditTrail( activity, req.getHeader().getuName(), value2) );
						userAdminService.updateLastActivity(userAdminService.getAdminByUsername( req.getHeader().getuName() ));
					}
						
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
		
	@RequestMapping(value = "/checkUpRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> checkUpRecord(@RequestBody String input) {
		
		LOG.traceEntry();
		APIResponse < HashMap<String, Object> > response = new APIResponse < HashMap<String, Object> > ();
		HashMap< String, Object > responseObject = new HashMap< String, Object > ();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		boolean result = false;
		String activity = null;
		
		try{
			
			LOG.info("CHECKUP RECORD");
			APIRequest < CheckUpRequest > req = getCheckUpRequest(input);
			UserAdmin userAdmin = userAdminService.getAdminByUsername( req.getHeader().getuName() );
			
			if (req.getHeader().getChannel().equals( Constant.CHANNEL_WEB )) {
				
				if (userAdmin == null) {
					
					statusTrx = StatusCode.USER_NOT_FOUND;
					
				} else {
					
					switch ( req.getHeader().getCommand() ) {
					case "save":
						
						LOG.info("SAVE");

						result = checkUpService.addCheckUpRecord( req.getPayload() );
						activity = Constant.ACTIVITY_ADD_CHECK_UP_RECORD;
						
						break;
					case "update":
						
						LOG.info("UPDATE");

						result = checkUpService.updateGrowthDtl( req.getPayload() );
						activity = Constant.ACTIVITY_UPDATE_CHECK_UP_RECORD;
						
						break;
					default:
						break;
					}
					
					if (!result) {
						statusTrx = StatusCode.FAILED_PROCESS;
					} else {
						User user = userService.getUserByID(req.getPayload().getUserId());
						Child child = userService.getChildByID(req.getPayload().getChildId());
						CheckUpRecord record = checkUpService.getCheckUpRecord(user.getId(), child.getId(), req.getPayload().getMstCode());
						GrowthDtl growth = checkUpService.getGrowthDtl( req.getPayload().getMstCode(), record.getId());
						CheckUpMaster cm = masterService.getMstCheckUpByCode(req.getPayload().getMstCode());
						
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
						object.put("checkUpDate", Util.formatDate(record.getCheckUpDate()));
						object.put("weight", String.valueOf(growth.getWeight()) + " KG");
						object.put("length", String.valueOf(growth.getLength()) + " CM");
						object.put("headDiameter", String.valueOf(growth.getHeadDiameter()) + " CM");
						object.put("weightNotes", Util.getWeightNotes (weightCategory) );
						object.put("lengthNotes", Util.getLengthNotes (lengthCategory));
						object.put("headDiameterNotes", Util.getHeadDiameterNotes (headDiameterCategory));
						byte[] pdfAsByte = ExportPDF.download(object, "report/template_checkup.jrxml");
						
						String message = MailHelper.checkUpMessage(user.getFullname());
						String filename = "Laporan Pemeriksaan Medis " + child.getFullname() + "(" + Util.formatDate(record.getCheckUpDate()) + ").pdf";
						mailService.sendEmail(user.getEmail(), mailTitleEditCheckUpRecord, message, filename, pdfAsByte);
						
						String value2 = Constant.VALUE_RECORD_CHECK_UP.replaceAll("<childName>", child.getFullname()).replaceAll("<weight>", String.valueOf(growth.getWeight()))
					    		  .replaceAll("<length>", String.valueOf(growth.getLength())).replaceAll("<headDiameter>", String.valueOf(growth.getHeadDiameter()));
						auditTrailService.saveAuditTrail(new AuditTrail( activity, req.getHeader().getuName(), value2 ) );
						userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
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
