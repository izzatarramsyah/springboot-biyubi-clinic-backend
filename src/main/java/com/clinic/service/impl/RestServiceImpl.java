package com.clinic.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.clinic.api.object.MessageRq;
import com.clinic.api.object.MessageRs;
import com.clinic.service.RestService;

@Service
public class RestServiceImpl implements RestService {

	protected static final Logger LOG = LogManager.getLogger();
	
	@Value("${url.whatsapp.reminder}")
	private String urlWhatsappReminder;
	
	public boolean post(MessageRq req)  {
		LOG.traceEntry();
		LOG.info("endpoint : " + urlWhatsappReminder );
		MessageRs res = null;
		try { 
			RestTemplate rest = new RestTemplate();
			res = rest.postForObject(urlWhatsappReminder, req, MessageRs.class);
			LOG.info("Response : " + res.getStatus());
		} catch ( Exception e ) {
			LOG.info("ERR " + e);
			return false;
		}
		LOG.traceExit();
		return res.getStatus();
	}
	
}
