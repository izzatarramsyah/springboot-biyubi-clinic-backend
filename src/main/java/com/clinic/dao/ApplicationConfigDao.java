package com.clinic.dao;

import java.util.List;

import com.clinic.entity.ApplicationConfig;

public interface ApplicationConfigDao {

	List < ApplicationConfig > get(String appsName, String serviceName);

}
