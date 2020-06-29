package com.sckroll.sckrollagile.domain.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;
import com.sckroll.sckrollagile.domain.common.event.DomainEventPublisher;
import com.sckroll.sckrollagile.domain.common.mail.MailManager;
import com.sckroll.sckrollagile.domain.common.mail.MessageVariable;
import com.sckroll.sckrollagile.domain.model.user.EmailAddressExistsException;
import com.sckroll.sckrollagile.domain.model.user.RegistrationException;
import com.sckroll.sckrollagile.domain.model.user.RegistrationManagement;
import com.sckroll.sckrollagile.domain.model.user.SimpleUser;
import com.sckroll.sckrollagile.domain.model.user.User;
import com.sckroll.sckrollagile.domain.model.user.UserRepository;
import com.sckroll.sckrollagile.domain.model.user.events.UserRegisteredEvent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImplTests {

	private RegistrationManagement registrationManagementMock;
	private DomainEventPublisher domainEventPublisherMock;
	private MailManager mailManagerMock;
	private UserRepository userRepositoryMock;
	private UserServiceImpl instance;

	@Before
	public void setup() {
		registrationManagementMock = mock(RegistrationManagement.class);
		domainEventPublisherMock = mock(DomainEventPublisher.class);
		mailManagerMock = mock(MailManager.class);
		userRepositoryMock = mock(UserRepository.class);
		instance = new UserServiceImpl(registrationManagementMock,
			domainEventPublisherMock, mailManagerMock, userRepositoryMock);
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
	public void register_유효한_명령일_경우_성공() throws RegistrationException {
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

	@Test
	public void loadUserByUsername_사용자명이_비어있으면_실패() {
		Exception exception = null;
		try {
			instance.loadUserByUsername("");
		} catch (Exception e) {
			exception = e;
		}

		assertNotNull(exception);
		assertTrue(exception instanceof UsernameNotFoundException);
		verify(userRepositoryMock, never()).findByUsername("");
		verify(userRepositoryMock, never()).findByEmailAddress("");
	}

	@Test
	public void loadUserByUsername_사용자명이_존재하지_않으면_실패() {
		String notExistUsername = "notExist";
		when(userRepositoryMock.findByUsername(notExistUsername)).thenReturn(null);
		Exception exception = null;
		try {
			instance.loadUserByUsername(notExistUsername);
		} catch (Exception e) {
			exception = e;
		}

		assertNotNull(exception);
		assertTrue(exception instanceof UsernameNotFoundException);
		verify(userRepositoryMock).findByUsername(notExistUsername);
		verify(userRepositoryMock, never()).findByEmailAddress(notExistUsername);
	}

	@Test
	public void loadUserByUsername_사용자명이_존재하면_성공()
		throws IllegalAccessException {

		String existUsername = "exist";
		User foundUser = User.create(existUsername, "test@sckroll.com", "encryptedPassword");

		// 사용자의 이름, 성 업데이트
		foundUser.updateName("First", "Last");

		// DB에서 사용자를 찾으려면 ID가 필요하지만
		// User 클래스에 ID 세터가 없으므로 Reflection을 사용하여 직접 입력
		// Reflection: 구체적인 클래스 타입을 몰라도 해당 클래스의
		//             메소드, 타입, 변수들을 접근할 수 있도록 해주는 Java API
		FieldUtils.writeField(foundUser, "id", 1L, true);

		// User의 실제 인스턴스를 생성하는 것 외에
		// 아래와 같이 사용자의 mock을 만드는 방법 사용 가능
		//
		// User mockUser = Mockito.mock(User.class);
		// when(mockUser.getUsername()).thenReturn(existUsername);
		// when(mockUser.getPassword()).thenReturn("encryptedPassword");
		// when(mockUser.getId()).thenReturn(1L);

		when(userRepositoryMock.findByUsername(existUsername)).thenReturn(foundUser);

		Exception exception = null;
		UserDetails userDetails = null;
		try {
			userDetails = instance.loadUserByUsername(existUsername);
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);
		verify(userRepositoryMock).findByUsername(existUsername);
		verify(userRepositoryMock, never()).findByEmailAddress(existUsername);
		assertNotNull(userDetails);
		assertEquals(existUsername, userDetails.getUsername());
		assertTrue(userDetails instanceof SimpleUser);
	}
}
