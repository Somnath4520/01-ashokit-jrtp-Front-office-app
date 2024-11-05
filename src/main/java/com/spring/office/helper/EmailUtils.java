package com.spring.office.helper;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private Logger logger = LoggerFactory.getLogger(EmailUtils.class); 
	
	public boolean sendMail(String subject, String body, String to) {
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true,CharEncoding.UTF_8);
			
			helper.setSubject(subject);
			
			helper.setText(body,true);
			helper.setTo(to);
			
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
