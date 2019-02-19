package com.hackerrank.order.service;

import java.io.IOException;

import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;

	public String sendmail() throws AddressException, MessagingException, IOException {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo("mcasatyakumar@gmail.com");
			helper.setText("Greetings :)");
			helper.setSubject("Mail From Spring Boot");
		} catch (Exception e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

}

