package com.sckroll.sckrollagile.infrastructure.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sckroll.sckrollagile.domain.model.user.User;
import com.sckroll.sckrollagile.domain.model.user.UserRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class HibernateUserRepositoryTests {

	@TestConfiguration
	public static class UserRepositoryTestContextConfiguration {

		@Bean
		public UserRepository userRepository(
			EntityManager entityManager) {
			return new HibernateUserRepository(entityManager);
		}
	}

	@Autowired
	private UserRepository repository;

	@Test(expected = PersistenceException.class)
	public void save_사용자명이_비어있으면_실패() {
		User invalidUser = User.create(null,
			"test@sckroll.com", "testPassword");
		repository.save(invalidUser);
	}

	@Test(expected = PersistenceException.class)
	public void save_이메일_주소가_비어있으면_실패() {
		User invalidUser = User.create("test",
			null, "testPassword");
		repository.save(invalidUser);
	}

	@Test(expected = PersistenceException.class)
	public void save_비밀번호가_비어있으면_실패() {
		User invalidUser = User.create("test",
			"test@sckroll.com", null);
		repository.save(invalidUser);
	}

	@Test
	public void save_모두_입력되어있으면_성공() {
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";
		User newUser = User.create(username, emailAddress, password);
		repository.save(newUser);

		assertNotNull("New user's id should be generated", newUser.getId());
		assertNotNull("New user's created date should be generated", newUser.getCreatedDate());
		assertEquals(username, newUser.getUsername());
		assertEquals(emailAddress, newUser.getEmailAddress());
		assertEquals("", newUser.getFirstName());
		assertEquals("", newUser.getLastName());
	}

	@Test
	public void save_사용자명이_이미_존재하면_실패() {
		// 이미 존재하는 사용자를 생성
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";
		User alreadyExist = User.create(username,
			emailAddress, password);
		repository.save(alreadyExist);

		try {
			User newUser = User.create(username,
				"new@sckroll.com", password);
			repository.save(newUser);
		} catch (Exception e) {
			assertEquals(ConstraintViolationException.class.toString(),
				e.getCause().getClass().toString());
		}
	}

	@Test
	public void save_이메일_주소가_이미_존재하면_실패() {
		// 이미 존재하는 사용자를 생성
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";
		User alreadyExist = User.create(username,
			emailAddress, password);
		repository.save(alreadyExist);

		try {
			User newUser = User.create("new",
				emailAddress, password);
			repository.save(newUser);
		} catch (Exception e) {
			assertEquals(ConstraintViolationException.class.toString(),
				e.getCause().getClass().toString());
		}
	}

	@Test
	public void findByEmailAddress_존재하지_않으면_빈_결과_리턴() {
		String emailAddress = "test@sckroll.com";
		User user = repository.findByEmailAddress(emailAddress);
		assertNull("No user should by found", user);
	}

	@Test
	public void findByEmailAddress_존재하면_결과_리턴() {
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";
		User newUser = User.create(username, emailAddress, password);
		repository.save(newUser);

		User found = repository.findByEmailAddress(emailAddress);
		assertEquals("Username should match", username, found.getUsername());
	}

	@Test
	public void findByUsername_존재하지_않으면_빈_값_리턴() {
		String username = "test";
		User user = repository.findByUsername(username);
		assertNull("No user should by found", user);
	}

	@Test
	public void findByUsername_존재하면_결과_리턴() {
		String username = "test";
		String emailAddress = "test@sckroll.com";
		String password = "testPassword";
		User newUser = User.create(username, emailAddress, password);
		repository.save(newUser);

		User found = repository.findByUsername(username);
		assertEquals("Email address should match", emailAddress, found.getEmailAddress());
	}
}
