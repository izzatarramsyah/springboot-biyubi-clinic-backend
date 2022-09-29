package com.clinic.service.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.clinic.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String to, String subject, String message, String attachemntFileName, byte [] attachement) 
			throws MessagingException, IOException{
		
		MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, true);
        if (attachemntFileName != null) {
            ByteArrayDataSource byteArrayDataSource=new ByteArrayDataSource(attachement, "application/pdf");
            helper.addAttachment(attachemntFileName, byteArrayDataSource);
        }

        mailSender.send(msg);
	}

}
