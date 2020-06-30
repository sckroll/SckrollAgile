package com.sckroll.sckrollagile.infrastructure.mail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.sckroll.sckrollagile.domain.common.mail.SimpleMessage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class AsyncMailerTests {

	private JavaMailSender mailSenderMock;
	private AsyncMailer instance;

	@Before
	public void setup() {
		mailSenderMock = mock(JavaMailSender.class);
		instance = new AsyncMailer(mailSenderMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_메시지가_null이면_실패() {
		instance.send(null);
	}

	@Test
	public void send_유효한_메시지이면_성공() {
		String from = "system@sckroll.com";
		String to = "console.output@sckroll.com";
		String subject = "TEST MESSAGE";
		String body = "Username: test, Email Address: test@sckroll.com";

		SimpleMessage message = new SimpleMessage(to, subject, body, from);
		instance.send(message);

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText("Username: test, Email Address: test@sckroll.com");
		verify(mailSenderMock).send(simpleMailMessage);
	}
}
