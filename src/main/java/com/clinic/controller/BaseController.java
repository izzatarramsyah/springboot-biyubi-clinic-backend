package com.clinic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.lang.reflect.Type; 

import com.clinic.api.request.APIRequest;
import com.clinic.api.object.CheckUpRequest; 
import com.clinic.api.object.ChildRegistration;
import com.clinic.api.object.InfoRq;
import com.clinic.api.object.VaccineRequest;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.UserAdmin;
import com.clinic.entity.VaccineRecord;
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
	     return t;  
	 }
	
	 public APIRequest < User > getRequestUser(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< User >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 } 
	 
	 public APIRequest < InfoRq > getInfoRq(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< InfoRq >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest < Child > getRequestChild(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< Child >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	
	 public APIRequest < VaccineRecord > getRequestVaccineRecord(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< VaccineRecord >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest < CheckUpRequest > getCheckUpRequest(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< CheckUpRequest >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest < VaccineRequest > getVaccineRq(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< VaccineRequest >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest < CheckUpMaster > getCheckUpMaster(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< CheckUpMaster >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest < UserAdmin > getRequestUserAdmin(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< UserAdmin >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
	 public APIRequest < ChildRegistration > getRequestChildRegistration(String content) throws Exception {
		 Gson gson = new Gson();
		 Type fooType = new TypeToken<APIRequest< ChildRegistration >>(){}.getType();
		 LOG.info("REQ::{}", content.toString());
		 return gson.fromJson(content, fooType);
	 }
	 
}	

