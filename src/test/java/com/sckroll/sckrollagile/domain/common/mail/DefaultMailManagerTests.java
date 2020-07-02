package com.sckroll.sckrollagile.domain.common.mail;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.Configuration;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DefaultMailManagerTests {

	@TestConfiguration
	static class DefaultMessageCreatorConfiguration {

		@Bean
		public FreeMarkerConfigurationFactoryBean
			getFreeMarkerConfiguration() {

			FreeMarkerConfigurationFactoryBean factoryBean =
				new FreeMarkerConfigurationFactoryBean();
			factoryBean.setTemplateLoaderPath("/mail-templates/");
			return factoryBean;
		}
	}

	@Autowired
	private Configuration configuration;
	private Mailer mailerMock;
	private DefaultMailManager instance;

	@Before
	public void setup() {
		mailerMock = mock(Mailer.class);
		instance = new DefaultMailManager("noreply@sckroll.com",
			mailerMock, configuration);
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_이메일_주소가_null이면_실패한다() {
		instance.send(null, "Test subject", "test.ftl");
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_이메일_주소가_비어있으면_실패한다() {
		instance.send("", "Test subject", "test.ftl");
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_이메일_주제가_null이면_실패한다() {
		instance.send("test@sckroll.com", null, "test.ftl");
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_이메일_주제가_비어있으면_실패한다() {
		instance.send("test@sckroll.com", "", "test.ftl");
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_템플릿_이름이_null이면_실패한다() {
		instance.send("test@sckroll.com", "Test subject", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void send_템플릿_이름이_비어있으면_실패한다() {
		instance.send("test@sckroll.com", "Test subject", "");
	}

	@Test
	public void send_파라미터가_유효하면_성공한다() {
		String to = "test@sckroll.com";
		String subject = "Test subject";
		String templateName = "test.ftl";

		instance.send(to, subject, templateName,
			MessageVariable.from("name", "test"));
		ArgumentCaptor<Message> messageArgumentCaptor =
			ArgumentCaptor.forClass(Message.class);
		verify(mailerMock).send(messageArgumentCaptor.capture());

		Message messageSent = messageArgumentCaptor.getValue();
		assertEquals(to, messageSent.getTo());
		assertEquals(subject, messageSent.getSubject());
		assertEquals("noreply@sckroll.com", messageSent.getFrom());
		assertEquals("Hello, test\n", messageSent.getBody());
	}
}
