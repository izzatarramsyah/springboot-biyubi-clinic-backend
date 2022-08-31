package com.clinic.service;

import java.io.IOException;
import javax.mail.MessagingException;

public interface MailService {

	void sendEmail (String to, String subject, String message, String path) throws MessagingException, IOException;
	
}
