package com.clinic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.lang.reflect.Type;

import com.clinic.api.request.APIRequest;
import com.clinic.api.object.InfoRq;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.VaccineMaster;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BaseController {

	 private static final Logger LOG = LogManager.getLogger(BaseController.class);
	 
	 protected <T> T ObjectMapper(String content, Class<T> valueType)
	            throws JsonParseException, JsonMappingException, IOException {
	     LOG.traceEntry();
	     ObjectMapper mapper = new ObjectMapper();
	     T t = (T) mapper.readValue(content, valueType);
	     LOG.traceExit();
	      //return (T) mapper.readValue(content, valueType);
	     return t;  
	 }
	
	 public APIRequest<User> getRequestUser(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest<User>>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 } 
	 
	 public APIRequest < InfoRq > getInfoRq(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< InfoRq >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest<Child> getRequestChild(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest<Child>>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest<VaccineMaster> getRequestVaccineMaster(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest<VaccineMaster>>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
}
