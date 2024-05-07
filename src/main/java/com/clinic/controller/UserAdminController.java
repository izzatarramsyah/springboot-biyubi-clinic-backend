package com.clinic.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.api.request.APIRequest;
import com.clinic.api.response.APIResponse;
import com.clinic.api.object.HeaderResponse;
import com.clinic.constant.Constant;
import com.clinic.constant.StatusCode;
import com.clinic.entity.UserAdmin;
import com.clinic.service.LogService;
import com.clinic.service.UserAdminService;

@CrossOrigin
@RestController
@RequestMapping("/userAdmin")
public class UserAdminController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(UserAdminController.class);

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	LogService logService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> login(@RequestBody String input) {

		LOG.traceEntry();
		APIResponse<HashMap<String, Object>> response = new APIResponse<HashMap<String, Object>>();
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		try {
			LOG.info("LOGIN");
			APIRequest<UserAdmin> req = getRequestUserAdmin(input);
			if (req.getHeader().getChannel().equals(Constant.CHANNEL_WEB)) {
				UserAdmin userAdmin = userAdminService.isValidUser(req.getPayload().getUsername(),
						req.getPayload().getPassword());
				if (userAdmin == null) {
					statusTrx = StatusCode.USER_NOT_FOUND;
				} else {
					boolean isValidSession = userAdminService.isValidSession(null, req.getPayload().getUsername());
					if (!isValidSession) {
						statusTrx = StatusCode.VALID_SESSION;
					} else {
						String sessionId = userAdmin.getSessionId() != null
								&& !"".equalsIgnoreCase(userAdmin.getSessionId())
										? userAdmin.getSessionId()
										: UUID.randomUUID().toString().replace("-", "").toUpperCase();
						userAdmin.setSessionId(sessionId);
						userAdmin.setLastActivity(new Date());
						userAdminService.updateLastActivity(userAdmin);
						userAdminService.updateSessionId(userAdmin);
						responseObject.put("object", userAdmin);
					}
				}

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

	@RequestMapping(value = "/checkSession", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> checkSession(@RequestBody String input) {

		LOG.traceEntry();
		APIResponse<HashMap<String, Object>> response = new APIResponse<HashMap<String, Object>>();
		StatusCode statusTrx = StatusCode.SUCCESS;
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		try {
			LOG.info("CHECKSESSION");
			APIRequest<UserAdmin> req = getRequestUserAdmin(input);
			if (req.getHeader().getChannel().equals(Constant.CHANNEL_WEB)) {
				UserAdmin user = userAdminService.getAdminByUsername(req.getHeader().getuName());
				if (user == null) {
					statusTrx = StatusCode.USER_NOT_FOUND;
				} else {
					boolean isValidSession = userAdminService.isValidSession(req.getHeader().getSession(),
							req.getHeader().getuName());
					if (!isValidSession) {
						LOG.info("FORCE LOGOUT");
						user.setSessionId(null);
						userAdminService.updateSessionId(user);
						statusTrx = StatusCode.INVALID_SESSION;
					}
				}

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

	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> logout(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse<HashMap<String, Object>> response = new APIResponse<HashMap<String, Object>>();
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		StatusCode statusTrx = StatusCode.SUCCESS;
		String username = null;
		try {
			LOG.info("LOGOUT");
			APIRequest<UserAdmin> req = getRequestUserAdmin(input);
			if (req.getHeader().getChannel().equals(Constant.CHANNEL_WEB)) {
				username = req.getPayload().getUsername();
				UserAdmin user = userAdminService.getAdminByUsername(username);
				user.setSessionId(null);
				userAdminService.updateSessionId(user);
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

	@RequestMapping(value = "/getLogs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<?> getLogs(@RequestBody String input) {
		LOG.traceEntry();
		APIResponse<HashMap<String, Object>> response = new APIResponse<HashMap<String, Object>>();
		HashMap<String, Object> responseObj = new HashMap<String, Object>();
		StatusCode statusTrx = StatusCode.SUCCESS_PROCESS;
		try {
			LOG.info("GET LIST LOGS");
			APIRequest<UserAdmin> req = getRequestUserAdmin(input);
			if (req.getHeader().getChannel().equals(Constant.CHANNEL_WEB)) {
				responseObj.put("object", logService.getLogs(req.getPayload().getUsername()));
				response.setPayload(responseObj);
				userAdminService.updateLastActivity(userAdminService.getAdminByUsername(req.getHeader().getuName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("ERR::[{}]:{}", e.getMessage());
			statusTrx = StatusCode.GENERIC_ERROR;
		}
		response.setHeader(new HeaderResponse(statusTrx.getCode(), statusTrx.getStatusDesc()));
		LOG.traceExit();
		return response;
	}

}
