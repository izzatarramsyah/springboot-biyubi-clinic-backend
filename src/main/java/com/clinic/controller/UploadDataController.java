package com.clinic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.api.object.CheckUpRequest;
import com.clinic.api.object.HeaderResponse;
import com.clinic.api.object.UploadData;
import com.clinic.api.object.UploadRequest;
import com.clinic.api.object.UploadResponse;
import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.constant.Constant;
import com.clinic.constant.StatusCode;
import com.clinic.entity.Log;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.UserAdmin;
import com.clinic.entity.VaccineMaster;
import com.clinic.entity.VaccineRecord;
import com.clinic.service.LogService;
import com.clinic.service.CheckUpService;
import com.clinic.service.MasterService;
import com.clinic.service.UserAdminService;
import com.clinic.service.UserService;
import com.clinic.service.VaccineService;
import com.clinic.util.Util;

@CrossOrigin
@RestController
@RequestMapping("/upload")
public class UploadDataController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(UploadDataController.class);

	@Autowired
	UserService userService;

	@Autowired
	CheckUpService checkUpService;

	@Autowired
	VaccineService vaccineService;

	@Autowired
	MasterService masterService;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	LogService logService;

	@RequestMapping(value = "/bulkProcess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> bulkProcess(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse<HashMap<String, Object>> response = new APIResponse<HashMap<String, Object>>();
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		List<UploadData> uploadData = new ArrayList<UploadData>();
		List<UploadResponse> uploadResponse = new ArrayList<UploadResponse>();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		String activity = null;
		int countSuccess = 0;
		int countFailed = 0;
		int row = 1;
		try {
			LOG.info("BULK PROCESS");
			APIRequest<UploadRequest> req = geUploadRequest(input);
			if (req.getHeader().getChannel().equals(Constant.CHANNEL_WEB)) {
				UserAdmin userAdmin = userAdminService.getAdminByUsername(req.getHeader().getuName());
				if (userAdmin == null) {
					statusTrx = StatusCode.USER_ADMIN_NOT_FOUND;
				} else {
					uploadData = req.getPayload().getData();
					switch (req.getHeader().getCommand()) {
						case Constant.USER_REGISTARTION:
							activity = Constant.ACTIVITY_UPLOAD_FILE_USER;
							for (UploadData data : uploadData) {
								boolean isNotValidRow = isNotValidRow(data, Constant.USER_REGISTARTION);
								if (!isNotValidRow) {
									String message = validateUserRegistration(data);
									if (!message.equals(Strings.EMPTY)) {
										countFailed++;
										String param = "Baris Ke - " + row++;
										uploadResponse.add(new UploadResponse(param, message));
									} else {
										if (userService.getUserByFullname(data.getNama_Orang_Tua()) == null) {
											User user = new User();
											user.setFullname(data.getNama_Orang_Tua());
											user.setPhone_no(data.getNo_Telepon());
											user.setEmail(data.getEmail());
											user.setAddress(data.getAlamat());
											if (userService.insertUser(user)) {
												countSuccess++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Berhasil"));
											} else {
												countFailed++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Gagal"));
											}
										}
									}
								} else {
									countFailed++;
									String param = "Baris Ke - " + row++;
									uploadResponse.add(new UploadResponse(param, "Data tidak boleh kosong"));
								}
							}
							break;
						case Constant.CHILD_REGISTARTION:
							activity = Constant.ACTIVITY_UPLOAD_FILE_CHILD;
							for (UploadData data : uploadData) {
								boolean isNotValidRow = isNotValidRow(data, Constant.CHILD_REGISTARTION);
								if (!isNotValidRow) {
									String message = validateChildRegistration(data);
									if (!message.equals(Strings.EMPTY)) {
										countFailed++;
										String param = "Baris Ke - " + row++;
										uploadResponse.add(new UploadResponse(param, message));
									} else {
										Child child = new Child();
										User user = userService.getUserByFullname(data.getNama_Orang_Tua());
										child.setUserId(user.getId());
										child.setFullname(data.getNama_Anak());
										child.setBirthDate(Util.formatDate(data.getTanggal_Lahir()));
										child.setGender(data.getJenis_Kelamin());
										child.setNotes(data.getCatatan());
										int id = userService.insertChild(child);
										if (id > 0) {
											CheckUpRequest checkHealth = new CheckUpRequest();
											checkHealth.setUserId(user.getId());
											checkHealth.setChildId(id);
											checkHealth.setWeight(data.getBerat_Badan());
											checkHealth.setLength(data.getPanjang_Badan());
											checkHealth.setHeadDiameter(data.getLingkar_Kepala());
											checkHealth.setMstCode("ACT_000");
											checkHealth.setBatch(0);
											checkHealth.setNotes(data.getCatatan());
											checkHealth.setCheckUpDate(Util.formatDate(data.getTanggal_Lahir()));
											if (checkUpService.addCheckUpRecord(userAdmin, checkHealth)) {
												countSuccess++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Berhasil"));
											} else {
												countFailed++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Gagal"));
											}
										}
									}
								} else {
									countFailed++;
									String param = "Baris Ke - " + row++;
									uploadResponse.add(new UploadResponse(param, "Data tidak boleh kosong"));
								}
							}
							break;
						case Constant.VACCINE_RECORD:
							activity = Constant.ACTIVITY_UPLOAD_VACCINE;
							for (UploadData data : uploadData) {
								boolean isNotValidRow = isNotValidRow(data, Constant.VACCINE_RECORD);
								if (!isNotValidRow) {
									String message = validateVaccineRecord(data);
									if (!message.equals(Strings.EMPTY)) {
										countFailed++;
										String param = "Baris Ke - " + row++;
										uploadResponse.add(new UploadResponse(param, message));
									} else {
										User user = userService.getUserByFullname(data.getNama_Orang_Tua());
										Child child = userService.getChildByFullname(data.getNama_Anak());
										VaccineMaster vm = masterService.getMstVaccineByName(data.getNama_Imunisasi());
										if (vaccineService.getVaccineRecord(user.getId(), user.getId(), data.getBulan(),
												vm.getVaccineCode()) == null) {
											VaccineRecord record = new VaccineRecord();
											record.setUserId(user.getId());
											record.setChildId(child.getId());
											record.setVaccineCode(vm.getVaccineCode());
											record.setBatch(data.getBulan());
											record.setNotes(data.getCatatan());
											record.setVaccineDate(Util.formatDate(data.getTanggal_Imunisasi()));
											if (vaccineService.addVaccineRecord(record)) {
												countSuccess++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Berhasil"));
												String value2 = Constant.VALUE_RECORD_VACCINE
														.replaceAll("<childName>", child.getFullname())
														.replaceAll("<vaccineName>", vm.getVaccineName())
														.replaceAll("<batch>", String.valueOf(data.getBulan()))
														.replaceAll("<notes>", data.getCatatan());
												// auditTrailService.saveAuditTrail(new AuditTrail(
												// Constant.ACTIVITY_ADD_VACCINE_RECORD, req.getHeader().getuName(),
												// value2) );
											} else {
												countFailed++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Gagal"));
											}
										}
									}
								} else {
									countFailed++;
									String param = "Baris Ke - " + row++;
									uploadResponse.add(new UploadResponse(param, "Data tidak boleh kosong"));
								}
							}
							break;
						case Constant.CHECKUP_RECORD:
							activity = Constant.ACTIVITY_UPLOAD_CHECK_UP;
							for (UploadData data : uploadData) {
								boolean isNotValidRow = isNotValidRow(data, Constant.CHECKUP_RECORD);
								if (!isNotValidRow) {
									String message = validateCheckupRecord(data);
									if (!message.equals(Strings.EMPTY)) {
										countFailed++;
										String param = "Baris Ke - " + row++;
										uploadResponse.add(new UploadResponse(param, message));
									} else {
										User user = userService.getUserByFullname(data.getNama_Orang_Tua());
										Child child = userService.getChildByFullname(data.getNama_Anak());
										CheckUpMaster cm = masterService.getListMstCheckUpByBatch(data.getBulan());
										if (checkUpService.getCheckUpRecord(user.getId(), child.getId(),
												cm.getCode()) == null) {
											CheckUpRequest checkHealth = new CheckUpRequest();
											checkHealth.setUserId(user.getId());
											checkHealth.setChildId(child.getId());
											checkHealth.setWeight(data.getBerat_Badan());
											checkHealth.setLength(data.getPanjang_Badan());
											checkHealth.setHeadDiameter(data.getLingkar_Kepala());
											checkHealth.setMstCode(cm.getCode());
											checkHealth.setBatch(data.getBulan());
											checkHealth.setNotes(data.getCatatan());
											checkHealth.setCheckUpDate(Util.formatDate(data.getTanggal_Pemeriksaan()));
											if (checkUpService.addCheckUpRecord(userAdmin, checkHealth)) {
												countSuccess++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Berhasil"));
												String value2 = Constant.VALUE_RECORD_CHECK_UP
														.replaceAll("<childName>", child.getFullname())
														.replaceAll("<weight>", String.valueOf(data.getBerat_Badan()))
														.replaceAll("<length>", String.valueOf(data.getPanjang_Badan()))
														.replaceAll("<headDiameter>",
																String.valueOf(data.getLingkar_Kepala()));
												// auditTrailService.saveAuditTrail(new AuditTrail(
												// Constant.ACTIVITY_ADD_CHECK_UP_RECORD, req.getHeader().getuName(),
												// value2 ) );
											} else {
												countFailed++;
												String param = "Baris Ke - " + row++;
												uploadResponse.add(new UploadResponse(param, "Gagal"));
											}
										}
									}
								} else {
									countFailed++;
									String param = "Baris Ke - " + row++;
									uploadResponse.add(new UploadResponse(param, "Data tidak boleh kosong"));
								}
							}
							break;
						default:
							break;
					}
					if (uploadResponse.size() > 0) {
						responseObject.put("countSuccess", countSuccess);
						responseObject.put("countFailed", countFailed);
						responseObject.put("object", uploadResponse);
					}

					if (activity != null) {
						String value2 = activity
								.replaceAll("<fileName>", req.getPayload().getFilename())
								.replaceAll("<successCount>", String.valueOf(countSuccess))
								.replaceAll("<failedCount>", String.valueOf(countFailed));
						logService.saveLog(new Log(
								Constant.ACTIVITY_UPLOAD_FILE_USER, req.getHeader().getuName(),
								value2));
					}

					userAdminService
							.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
				}
			} else {
				statusTrx = StatusCode.INVALID_CHANNEL;
			}
			response.setPayload(responseObject);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		response.setHeader(new HeaderResponse(statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}

	private boolean isNotValidRow(UploadData data, String type) {
		boolean result = false;
		if (type.equals(Constant.USER_REGISTARTION)) {
			if (Objects.isNull(data.getNama_Orang_Tua()) || Objects.isNull(data.getNo_Telepon()) ||
					Objects.isNull(data.getEmail()) || Objects.isNull(data.getAlamat())) {
				result = true;
			}
		} else if (type.equals(Constant.CHILD_REGISTARTION)) {
			if (Objects.isNull(data.getNama_Orang_Tua()) || Objects.isNull(data.getNama_Anak()) ||
					Objects.isNull(data.getTanggal_Lahir()) || Objects.isNull(data.getJenis_Kelamin()) ||
					Objects.isNull(data.getBerat_Badan()) || Objects.isNull(data.getPanjang_Badan()) ||
					Objects.isNull(data.getLingkar_Kepala()) || Objects.isNull(data.getCatatan())) {
				result = true;
			}
		} else if (type.equals(Constant.CHECKUP_RECORD)) {
			if (Objects.isNull(data.getNama_Orang_Tua()) || Objects.isNull(data.getNama_Anak()) ||
					Objects.isNull(data.getBulan()) || Objects.isNull(data.getTanggal_Pemeriksaan()) ||
					Objects.isNull(data.getBerat_Badan()) || Objects.isNull(data.getPanjang_Badan()) ||
					Objects.isNull(data.getLingkar_Kepala()) || Objects.isNull(data.getCatatan())) {
				result = true;
			}
		} else if (type.equals(Constant.VACCINE_RECORD)) {
			if (Objects.isNull(data.getNama_Orang_Tua()) || Objects.isNull(data.getNama_Anak()) ||
					Objects.isNull(data.getNama_Imunisasi()) || Objects.isNull(data.getTanggal_Imunisasi()) ||
					Objects.isNull(data.getBulan()) || Objects.isNull(data.getCatatan())) {
				result = true;
			}
		} else {
			result = true;
		}
		return result;
	}

	private String validateUserRegistration(UploadData data) {
		StringBuilder message = new StringBuilder();
		if (!Util.isValidNumber(data.getNo_Telepon())) {
			message.append("Format Nomor Telepon Tidak Sesuai | ");
		}
		if (!Util.isValidPrefixNum(data.getNo_Telepon())) {
			message.append("Format Prefix Nomor Telepon Tidak Sesuai | ");
		}
		if (!Util.isValidEmail(data.getEmail())) {
			message.append("Format Email Tidak Sesuai | ");
		}
		return message.toString().substring(0, message.length() - 3);
	}

	private String validateChildRegistration(UploadData data) {
		StringBuilder message = new StringBuilder();
		if (!Util.isValidFormatDate(data.getTanggal_Lahir())) {
			message.append("Format Tanggal Lahir (DD-MM-YYYY) tidak valid | ");
		}
		return message.toString().substring(0, message.length() - 3);
	}

	private String validateCheckupRecord(UploadData data) {
		StringBuilder message = new StringBuilder();
		if (!Util.isValidFormatDate(data.getTanggal_Pemeriksaan())) {
			message.append("Format Tanggal Pemeriksaan (DD-MM-YYYY) tidak valid | ");
		}
		return message.toString().substring(0, message.length() - 3);
	}

	private String validateVaccineRecord(UploadData data) {
		StringBuilder message = new StringBuilder();
		if (!Util.isValidFormatDate(data.getTanggal_Imunisasi())) {
			message.append("Format Tanggal Imunisasi (DD-MM-YYYY) tidak valid | ");
		}
		return message.toString().substring(0, message.length() - 3);
	}

}
