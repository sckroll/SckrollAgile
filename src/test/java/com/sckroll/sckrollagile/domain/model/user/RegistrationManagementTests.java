package com.sckroll.sckrollagile.domain.model.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sckroll.sckrollagile.domain.common.security.PasswordEncryptor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class RegistrationManagementTests {

	private UserRepository repositoryMock;
	private PasswordEncryptor passwordEncryptorMock;
	private RegistrationManagement instance;

	@Before
	public void setup() {
		repositoryMock = mock(UserRepository.class);
		passwordEncryptorMock = mock(PasswordEncryptor.class);
		instance = new RegistrationManagement(
			repositoryMock, passwordEncryptorMock);
	}

	@Test(expected = UsernameExistsException.class)
	public void register_존재하는_사용자명이_있다면_실패()
		throws RegistrationException {

		String username = "exist";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";

		// 이미 존재하는 사용자임을 알려주고자 빈 객체 반환
		when(repositoryMock.findByUsername(username)).thenReturn(new User());
		instance.register(username, emailAddress, password);
	}

	@Test(expected = EmailAddressExistsException.class)
	public void register_존재하는_이메일_주소가_있다면_실패()
		throws RegistrationException {

		String username = "test";
		String emailAddress = "exist@sckroll.com";
		String password = "testPassword";

		// 이미 존재하는 사용자임을 알려주고자 빈 객체 반환
		when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
		instance.register(username, emailAddress, password);
	}

	@Test
	public void register_이메일_주소에_대문자가_포함되었다면_소문자로_변환_후_성공()
		throws RegistrationException {

		String username = "test";
		String emailAddress = "Test@Sckroll.com";
		String password = "testPassword";

		instance.register(username, emailAddress, password);
		User userToSave = User.create(username,
			emailAddress.toLowerCase(), password);
		verify(repositoryMock).save(userToSave);
	}

	@Test
	public void register_새로운_사용자는_성공() throws RegistrationException {
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";
		String encryptedPassword = "EncryptedPassword";
		User newUser = User.create(username, emailAddress, encryptedPassword);

		// 리파지토리 목 설정
		// 사용자가 존재하지 않음을 나타내고자 null 값 반환
		when(repositoryMock.findByUsername(username)).thenReturn(null);
		when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
		doNothing().when(repositoryMock).save(newUser);

		// passworedEncryptor 목 설정
		when(passwordEncryptorMock.encrypt(password))
			.thenReturn("EncryptedPassword");

		User savedUser = instance.register(username, emailAddress, password);

		// repositoryMock의 메소드가 특정 순서대로 호출되는지 검증
		InOrder inOrder = inOrder(repositoryMock);
		inOrder.verify(repositoryMock).findByUsername(username);
		inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
		inOrder.verify(repositoryMock).save(newUser);
		verify(passwordEncryptorMock).encrypt(password);
		assertEquals("Saved user's password should be encrypted",
			encryptedPassword, savedUser.getPassword());
	}
}
