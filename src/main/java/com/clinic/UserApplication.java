package com.clinic;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableRetry
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.clinic"})
public class UserApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
	        /* Setting Spring Boot SetTimeZone
	            TimeZone.setDefault(TimeZone.getTimeZone("GMT+7:00"));
	            System.out.println("Spring boot application running in WIB timezone :" + new Date());   // It will print UTC timezone
	        */
	}
	
}
