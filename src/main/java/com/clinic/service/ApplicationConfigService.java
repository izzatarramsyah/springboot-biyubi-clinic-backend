package com.clinic.service;

import java.util.List;

import com.clinic.entity.ApplicationConfig;

public interface ApplicationConfigService {

	List < ApplicationConfig > get(String appsName, String serviceName);

}
