package com.sckroll.sckrollagile.domain.application.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;
import com.sckroll.sckrollagile.domain.common.event.DomainEventPublisher;
import com.sckroll.sckrollagile.domain.common.mail.MailManager;
import com.sckroll.sckrollagile.domain.common.mail.MessageVariable;
import com.sckroll.sckrollagile.domain.model.user.EmailAddressExistsException;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;
import com.sckroll.sckrollagile.domain.model.user.RegistrationManagement;
import com.sckroll.sckrollagile.domain.model.user.User;
import com.sckroll.sckrollagile.domain.model.user.events.UserRegisteredEvent;

import org.junit.Before;
import org.junit.Test;

public class UserServiceImplTests {

	private RegistrationManagement registrationManagementMock;
	private DomainEventPublisher domainEventPublisherMock;
	private MailManager mailManagerMock;
	private UserServiceImpl instance;

	@Before
	public void setup() {
		registrationManagementMock = mock(RegistrationManagement.class);
		domainEventPublisherMock = mock(DomainEventPublisher.class);
		mailManagerMock = mock(MailManager.class);
		instance = new UserServiceImpl(registrationManagementMock,
			domainEventPublisherMock, mailManagerMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void register_null을_전달하면_실패() throws
		RegistrationException {
		instance.register(null);
	}

	@Test(expected = RegistrationException.class)
	public void register_존재하는_사용자명이_있다면_실패() throws
		RegistrationException {
		String username = "exist";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";

		doThrow(EmailAddressExistsException.class)
			.when(registrationManagementMock)
			.register(username, emailAddress, password);

		RegistrationCommand command =
			new RegistrationCommand(username, emailAddress, password);
		instance.register(command);
	}

	@Test(expected = RegistrationException.class)
	public void register_존재하는_이메일_주소가_있다면_실패() throws
		RegistrationException {
		String username = "test";
		String emailAddress = "exist@sckroll.com";
		String password = "testPassword";

		doThrow(EmailAddressExistsException.class)
			.when(registrationManagementMock)
			.register(username, emailAddress, password);

		RegistrationCommand command =
			new RegistrationCommand(username, emailAddress, password);
		instance.register(command);
	}

	@Test
	public void register_유효한_명령일_경우_성공() throws
		RegistrationException {
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";

		User newUser = User.create(username, emailAddress, password);
		when(registrationManagementMock
				.register(username, emailAddress, password))
			.thenReturn(newUser);

		RegistrationCommand command =
			new RegistrationCommand(username, emailAddress, password);
		instance.register(command);

		verify(mailManagerMock).send(
			emailAddress,
			"SckrollAgile에 가입하신 것을 환영합니다!",
			"welcome.ftl",
			MessageVariable.from("user", newUser)
		);
		verify(domainEventPublisherMock)
			.publish(new UserRegisteredEvent(newUser));
	}
}
